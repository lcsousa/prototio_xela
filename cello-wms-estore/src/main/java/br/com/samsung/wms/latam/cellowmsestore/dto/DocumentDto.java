package br.com.samsung.wms.latam.cellowmsestore.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {

    private Long documentId;
    private String documentNumber;
    private String notaFiscal;
    private LocalDate documentDate;
    private BigDecimal documentValue;
    private String currencyCode;
}
