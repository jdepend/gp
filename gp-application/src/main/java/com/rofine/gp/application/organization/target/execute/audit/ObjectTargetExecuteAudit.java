package com.rofine.gp.application.organization.target.execute.audit;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.rofine.gp.platform.entity.IdEntity;

@Entity
@Table(name="app_target_execute_audit")
public class ObjectTargetExecuteAudit extends IdEntity{
	
	
	
	public static final String State_Fill_Auditing = "fill_auditing";

}
