package com.baidu.gcrm.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "g_file_read_record")
public class FileReadRecord implements Serializable {

	private static final long serialVersionUID = -8336467824768386433L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "filename", nullable = false)
	private String filename;
	
	@Column(name = "read_time", nullable = false)
	private Date readTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
}
