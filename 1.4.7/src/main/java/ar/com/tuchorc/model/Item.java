package ar.com.tuchorc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This entity consists of a textual description up to 25 chars, which will be part of a sortable list.
 *
 * @author tuchorc
 */
@ApiModel(description = "Each Item of the list.")
@Entity
@Table(name = "sb_test_item")
public class Item implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_id")
	private Long itemId;

	@ApiModelProperty(notes = "Textual description up to 25 chars")
	@Column(name = "description", nullable = false, unique = true)
	private String description;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
