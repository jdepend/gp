//Source file: E:\\my_workspace\\workprojects\\201605��Ʒ�з�\\project\\gp-domain\\src\\main\\java\\com\\rofine\\gp\\domain\\DesignModel\\DesignElement\\domain\\organization\\target\\scheme\\ObjectTarget.java

package com.rofine.gp.domain.organization.target.scheme.model;

import java.util.ArrayList;
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
import com.rofine.gp.domain.organization.target.execute.model.ObjectTargetExecute;
import com.rofine.gp.domain.organization.target.target.frequency.TargetFrequency;
import com.rofine.gp.domain.organization.target.target.frequency.TargetFrequencyType;
import com.rofine.gp.domain.organization.target.target.frequency.TargetFrequencyTypeFactory;
import com.rofine.gp.platform.entity.IdEntity;

@Entity
@Table(name = "domain_object_target")
public class ObjectTarget extends IdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scheme_id")
	private Scheme scheme;

	@Column
	private Float score;

	@Column
	private int weight;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "object_id")
	private SchemeObject object;

	@Column(name = "object_code")
	private String objectCode;

	@Column(name = "object_type")
	private String objectType;

	@Column(name = "role_id")
	private String roleId;

	@Column(name = "fill_id")
	private String fillId;

	@OneToMany(mappedBy = "objectTarget", cascade = { CascadeType.ALL })
	private List<ObjectTargetExecute> objectTargetExecutes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "target_id")
	private Target target;

	/**
	 * @throws TargetException
	 * @roseuid 573A70BC0137
	 */
	public void createExecutes() throws TargetException {

		objectTargetExecutes = new ArrayList<ObjectTargetExecute>();

		TargetFrequencyType targetFrequencyType = TargetFrequencyTypeFactory.create(this.target.getFrequencyType());

		List<TargetFrequency> targetFrequencys = targetFrequencyType.createTargetFrequencys();
		for (TargetFrequency targetFrequency : targetFrequencys) {
			objectTargetExecutes.add(new ObjectTargetExecute(targetFrequency, this, target));
		}

	}

	/**
	 * @roseuid 573ABCCE0094
	 */
	protected void setScore() {

	}

	/**
	 * @roseuid 573ADE2202D2
	 */
	public void calculate() {

	}

	public SchemeObject getObject() {
		return object;
	}

	public void setObject(SchemeObject object) {
		this.object = object;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public List<ObjectTargetExecute> getObjectTargetExecutes() {
		return objectTargetExecutes;
	}

	public void setObjectTargetExecutes(List<ObjectTargetExecute> objectTargetExecutes) {
		this.objectTargetExecutes = objectTargetExecutes;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getFillId() {
		return fillId;
	}

	public void setFillId(String fillId) {
		this.fillId = fillId;
	}

	public void save() {
		this.getRepo("objectTargetRepo").save(this);
	}

}
