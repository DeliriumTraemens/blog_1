package com.nick.blog_1.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
public class Topic {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private Long parentId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Topic parentTopic;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parentTopic")
	private Set<Topic> childrenSet;
	
	private String name;
	
	private String description;
	
	private String imageUrl;
	
	private boolean top=false;
	
	
//Constructors
	
	public Topic() {
	}
	
	public Topic(Long parentId, Topic parentTopic, Set<Topic> childrenSet, String name, String description, String imageUrl, boolean top) {
		this.parentId = parentId;
		this.parentTopic = parentTopic;
		this.childrenSet = childrenSet;
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.top = top;
	}
	
	//Equals
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (! (o instanceof Topic)) {
			return false;
		}
		Topic topic = (Topic) o;
		return isTop() == topic.isTop() &&
				       getId().equals(topic.getId()) &&
				       getParentId().equals(topic.getParentId()) &&
				       Objects.equals(getName(), topic.getName()) &&
				       getDescription().equals(topic.getDescription()) &&
				       Objects.equals(getImageUrl(), topic.getImageUrl());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId(), getParentId(), getName(), getDescription(), getImageUrl(), isTop());
	}
	
	
	//Get-Setters
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public Topic getParentTopic() {
		return parentTopic;
	}
	
	public void setParentTopic(Topic parentTopic) {
		this.parentTopic = parentTopic;
	}
	
	public Set<Topic> getChildrenSet() {
		return childrenSet;
	}
	
	public void setChildrenSet(Set<Topic> childrenSet) {
		this.childrenSet = childrenSet;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public boolean isTop() {
		return top;
	}
	
	public void setTop(boolean top) {
		this.top = top;
	}
	
	
	//ToString
	
//	@Override
//	public String toString() {
//		return "\nTopic{" +
//				       "id=" + id +
//				       ", parentId=" + parentId +
//				       ", name='" + name + '\'' +
//				       ", description='" + description + '\'' +
//				       '}';
//	}
	
	@Override
	public String toString() {
		return "Topic{" +
				       "id=" + id +
				       ", parentId=" + parentId +'\'' +
				       ", name='" + name + '\'' +
				       ", description='" + description + '\'' +
				       ", imageUrl='" + imageUrl + '\'' +
				       ", \nchildrenSet=" + childrenSet +
				       '}';
	}
	
	
	//
}
