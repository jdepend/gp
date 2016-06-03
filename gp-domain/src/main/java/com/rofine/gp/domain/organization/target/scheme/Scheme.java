//Source file: E:\\my_workspace\\workprojects\\201605��Ʒ�з�\\project\\gp-domain\\src\\main\\java\\com\\rofine\\gp\\domain\\DesignModel\\DesignElement\\domain\\organization\\target\\scheme\\Scheme.java

package com.rofine.gp.domain.organization.target.scheme;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import com.rofine.gp.domain.organization.target.TargetException;
import com.rofine.gp.domain.organization.target.execute.ObjectTargetExecute;
import com.rofine.gp.platform.entity.IdEntity;
import com.rofine.gp.platform.exception.GpException;

@Entity
@Table(name = "scheme", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
public class Scheme extends IdEntity {

	@NotEmpty
	@Column
	private String name;

	@Column
	private String state;

	@Column
	private int year;

	@Column(name = "target_level_count")
	private int targetLevelCount;

	@OneToMany(mappedBy = "scheme", cascade = { CascadeType.ALL })
	@OrderBy("priority ASC")
	private List<Target> targets;

	@OneToMany(mappedBy = "scheme", cascade = { CascadeType.ALL })
	private List<TargetType> targetTypes;

	@OneToMany(mappedBy = "scheme", cascade = { CascadeType.ALL })
	private List<SchemeObjectGroup> schemeObjectGroup;

	@OneToMany(mappedBy = "scheme", cascade = { CascadeType.ALL })
	private List<SchemeObject> schemeObjects;

	public static final String State_Init = "init";

	public static final String State_Started = "started";

	public static final String State_Closed = "closed";

	/**
	 * @throws TargetException
	 * @roseuid 573C0F2901EB
	 */
	public void start() throws TargetException {

		checkStart();

		doStart();
	}

	/**
	 * @param targetId
	 * @param objectIds
	 * @throws GpException
	 * @roseuid 573C106F01BC
	 */
	public void target2object(String targetId, List<SchemeObjectVO> objects) throws TargetException {

		for (SchemeObjectVO object : objects) {

			L: for (SchemeObject schemeObject : this.schemeObjects) {
				if (schemeObject.getId().equals(object.getObjectId())) {
					object.setObject(schemeObject);
					break L;
				}
			}
			if (object.getObject() == null) {
				throw new TargetException("没有发现对应的被考核对象objectId=" + object.getObjectId());
			}
		}

		Target theTarget = targets.stream().filter(target -> target.getId().equals(targetId)).findFirst().get();

		if (theTarget == null) {
			throw new TargetException("没有发现对应的考核指标targetId=" + targetId);
		}

		theTarget.relationObjects(objects);
	}

	/**
	 * @return Boolean
	 * @throws TargetException
	 * @roseuid 573C16610395
	 */
	protected void checkStart() throws TargetException {
		if (this.targets.size() == 0) {
			throw new TargetException("考核方案[" + this.name + "]没有关联指标");
		}
		if (this.targetLevelCount > 0) {
			for (TargetType type : this.targetTypes) {
				if (type.getLevel() + 1 < this.targetLevelCount) {
					if (type.getChildren().size() == 0) {
						throw new TargetException("指标类型[" + type.getName() + "]没有关联指标类型");
					}
				} else {
					if (type.getTargets().size() == 0) {
						throw new TargetException("指标类型[" + type.getName() + "]没有关联指标");
					}
				}
			}
		}
		if (this.schemeObjects.size() == 0) {
			throw new TargetException("考核方案[" + this.name + "]没有关联被考核单元");
		}
		for (Target target : this.targets) {
			if (!target.isRelationObject()) {
				throw new TargetException("指标[" + target.getName() + "]没有关联被考核单元");
			}
		}
	}

	/**
	 * @roseuid 573C200E01F6
	 */
	protected void doStart() {
		this.state = State_Started;

		for (Target target : targets) {
			for (ObjectTarget objectTarget : target.getObjectTargets()) {
				for (ObjectTargetExecute execute : objectTarget.getObjectTargetExecutes()) {
					execute.start();
				}
			}
		}

		this.save();

	}

	/**
	 * @roseuid 573C224D015D
	 */
	public void save() {
		this.getRepo("schemeRepo").save(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getTargetLevelCount() {
		return targetLevelCount;
	}

	public void setTargetLevelCount(int targetLevelCount) {
		this.targetLevelCount = targetLevelCount;
	}

	public void setTargetTypes(List<TargetType> targetTypes) {
		this.targetTypes = targetTypes;
	}

	public void setTargets(List<Target> targets) {
		this.targets = targets;
	}

	public List<Target> getTargets() {
		return targets;
	}

	public List<TargetType> getTargetTypes() {
		return targetTypes;
	}

	public List<SchemeObjectGroup> getSchemeObjectGroup() {
		return schemeObjectGroup;
	}

	public void setSchemeObjectGroup(List<SchemeObjectGroup> schemeObjectGroup) {
		this.schemeObjectGroup = schemeObjectGroup;
	}

	public List<SchemeObject> getSchemeObjects() {
		return schemeObjects;
	}

	public void setSchemeObjects(List<SchemeObject> schemeObjects) {
		this.schemeObjects = schemeObjects;
	}
}