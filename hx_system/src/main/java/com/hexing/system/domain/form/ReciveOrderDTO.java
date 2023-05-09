package com.hexing.system.domain.form;

import lombok.Data;

import java.util.List;

@Data
public class ReciveOrderDTO {

    private List<OrderForm> data;

    private List<MilestoneForm> pay;
}
