package com.baidu.gcrm.publish.web.vo;

import java.util.List;

public class ProofListVO {
	private Long id;
	
	private List<ProofVO> proofList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ProofVO> getProofList() {
		return proofList;
	}

	public void setProofList(List<ProofVO> proofList) {
		this.proofList = proofList;
	}
	
}
