package com.maotion.covid19api.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "stats")
public class Case {
    @Id
    private String id;
    private String provinceOrState;
    private String countryOrRegion;
    private String lastUpdated;
    private Long confirmed;
    private Long deaths;
    private Long recovered;
    private Double latitude;
    private Double longitude;
}
