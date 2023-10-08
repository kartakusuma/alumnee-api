package id.my.garasikuzu.alumneeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataResponse {
    private int code;
    private String status;
    private Object data;
}
