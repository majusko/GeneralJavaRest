package sk.kapusta.entity;

import java.util.Date;

public class AccessToken {
	
	private Long accessTokenId;
	private Long userId;
	private String installationId;
	private String accessToken;
	private String refreshToken;
	private Date expireToken;
	
	
	public Long getAccessTokenId() {
		return accessTokenId;
	}
	public void setAccessTokenId(Long accessTokenId) {
		this.accessTokenId = accessTokenId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public Date getExpireToken() {
		return expireToken;
	}
	public void setExpireToken(Date expireToken) {
		this.expireToken = expireToken;
	}
	
	public String getInstallationId() {
		return installationId;
	}
	public void setInstallationId(String installationId) {
		this.installationId = installationId;
	}
	
	@Override
	public String toString() {
		return "AccessToken [accessTokenId=" + accessTokenId + ", userId="
				+ userId + ", installationId=" + installationId
				+ ", accessToken=" + accessToken + ", refreshToken="
				+ refreshToken + ", expireToken=" + expireToken + "]";
	}

}
