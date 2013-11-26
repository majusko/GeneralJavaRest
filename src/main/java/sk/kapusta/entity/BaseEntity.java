package sk.kapusta.entity;

public class BaseEntity {

	private String entityId;
	private Long revisionNumber;

	public Long getRevisionNumber() {
		return revisionNumber;
	}

	public void setRevisionNumber(Long revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String l) {
		this.entityId = l;
	}

	@Override
	public String toString() {
		return "BaseEntity [entityId=" + entityId + ", revisionNumber="
				+ revisionNumber + "]";
	}

}
