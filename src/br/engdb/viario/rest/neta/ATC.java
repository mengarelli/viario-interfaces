package br.engdb.viario.rest.neta;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ATC {
	private String operationType; // Update or Delete
	private String atcId;
	private String atcCode;
	private String atcDescription;
	private String atcShortDescription;
	private String uoParentId;
	private List<ATCRelation> atcRelation = new LinkedList<>();

	public List<ATCRelation> getAtcRelation() {
		return atcRelation;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getAtcId() {
		return atcId;
	}
	public void setAtcId(String atcId) {
		this.atcId = atcId;
	}
	public String getAtcCode() {
		return atcCode;
	}
	public void setAtcCode(String atcCode) {
		this.atcCode = atcCode;
	}
	public String getAtcDescription() {
		return atcDescription;
	}
	public void setAtcDescription(String atcDescription) {
		this.atcDescription = atcDescription;
	}
	public String getAtcShortDescription() {
		return atcShortDescription;
	}
	public void setAtcShortDescription(String atcShortDescription) {
		this.atcShortDescription = atcShortDescription;
	}
	public String getUoParentId() {
		return uoParentId;
	}
	public void setUoParentId(String uoParentId) {
		this.uoParentId = uoParentId;
	}

	public static class ATCRelation {
		@JsonProperty(value = "UOID")
		private String uoid;
		@JsonProperty(value = "UOParentCodeType")
		private String uoParentCodeType;
		@JsonProperty(value = "UOParentType")
		private String uoParentType;
		@JsonProperty(value = "OperationType")
		private String operationType;
		@JsonProperty(value = "SabespCode")
		private String sabespCode;
		
		public ATCRelation(String uoid, String uoParentCodeType, String sabespCode) {
			super();
			this.uoid = uoid;
			this.uoParentCodeType = uoParentCodeType;
			this.sabespCode = sabespCode;
		}
		public String getUoid() {
			return uoid;
		}
		public void setUoid(String uoid) {
			this.uoid = uoid;
		}
		public String getUoParentCodeType() {
			return uoParentCodeType;
		}
		public void setUoParentCodeType(String uoParentCodeType) {
			this.uoParentCodeType = uoParentCodeType;
		}
		public String getOperationType() {
			return operationType;
		}
		public void setOperationType(String operationType) {
			this.operationType = operationType;
		}
		public String getSabespCode() {
			return sabespCode;
		}
		public void setSabespCode(String sabespCode) {
			this.sabespCode = sabespCode;
		}
		public String getUoParentType() {
			return uoParentType;
		}
		public void setUoParentType(String uoParentType) {
			this.uoParentType = uoParentType;
		}
	}
}
