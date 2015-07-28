package com.baidu.gcrm.bpm.web.helper;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class ParticipantBean {
	private String participantId;
	
	private List<String> usernames;
	
	public ParticipantBean() {
		
	}

	public ParticipantBean(String participantId) {
		this.participantId = participantId;
	}

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((participantId == null) ? 0 : participantId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParticipantBean other = (ParticipantBean) obj;
		if (participantId == null) {
			if (other.participantId != null)
				return false;
		} else if (!participantId.equals(other.participantId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (CollectionUtils.isNotEmpty(usernames)) {
			String[] names = new String[usernames.size()];
			usernames.toArray(names);
			return "Participant [participantId=" + participantId + ", usernames=" + Arrays.toString(names) + "]";
		} else {
			return "Participant [participantId=" + participantId + ", usernames empty]";
		}
	}
	
	
}
