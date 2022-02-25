package br.com.samsung.wms.latam.cellowmsestore.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_TEST")
//@SequenceGenerator(name = "SEQ_MA_CELL", sequenceName = "SEQ_MA_CELL", allocationSize = 1)
public class TestEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEST_ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "TEST_NAME", nullable = false, length = 100)
	private String name;
	
	@Column(name = "TEST_DESC", nullable = false, length = 40)
	private String description;
	
	@Column(name = "INSERT_DATE", nullable = false, length = 23)
	private LocalDateTime insertDate;
	
	@Column(name = "INSERT_ID", nullable = false)
	private String insertId;

}
