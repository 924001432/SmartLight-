package com.example.light.dto;

import com.example.light.entity.HandleResult;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class handleResultDto extends HandleResult {
    public handleResultDto(HandleResult handleResult) {
        this.setHandleResultId(handleResult.getHandleResultId());
        this.setHandleResultText(handleResult.getHandleResultText());
    }
    private Integer orderId;
    private List<String> imageList;
}
