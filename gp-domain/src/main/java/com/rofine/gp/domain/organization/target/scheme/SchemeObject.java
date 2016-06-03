//Source file: E:\\my_workspace\\workprojects\\201605��Ʒ�з�\\project\\gp-domain\\src\\main\\java\\com\\rofine\\gp\\domain\\DesignModel\\DesignElement\\domain\\organization\\target\\scheme\\SchemeObject.java

package com.rofine.gp.domain.organization.target.scheme;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rofine.gp.domain.organization.target.TargetException;

@Entity
@Table(name = "scheme_object")
public class SchemeObject extends SchemeObjectComponent {

	@Column
	private Float score;

	@Column
	private String type;

	@Column(name="object_id")
	private String objectId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private SchemeObjectGroup group;
	
	@OneToMany(mappedBy = "object", cascade = { CascadeType.ALL })
	private List<ObjectTarget> objectTargets;

	/**
	 * @roseuid 573ADE31003A
	 */
	public void calculate() {

	}

	public SchemeObjectGroup getGroup() {
		return group;
	}

	public void setGroup(SchemeObjectGroup group) {
		this.group = group;
	}
	
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public List<ObjectTarget> getObjectTargets() {
		return objectTargets;
	}

	public void setObjectTargets(List<ObjectTarget> objectTargets) {
		this.objectTargets = objectTargets;
	}

	public void save() throws TargetException {
		this.getRepo("schemeObjectRepo").save(this);
	}

}
