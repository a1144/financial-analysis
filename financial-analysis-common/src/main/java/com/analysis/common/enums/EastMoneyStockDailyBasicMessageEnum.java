package com.analysis.common.enums;

public enum EastMoneyStockDailyBasicMessageEnum {
    /**
    * @Description:  
    * @param: null 
    * @return: 
    * @Date: 2020/4/26 
    */ 
    STOCK_CODE("f57","stockCode","股票代码"),
    STOCK_NAME("f58","stockName","股票名称"),
    PRESENT_PRICE("f43","presentPrice","当前价格"),
    RISE_OR_FALL("f169","riseOrFall","涨跌"),
    RISE_OR_FALL_RATE("f170","riseOrFallRate","涨幅"),
    HIGHEST_PRICE("f44","highestPrice","最高价格"),
    LOWEST_PRICE("f45","lowestPrice","最低价格"),
    OPENING_PRICE("f46","openingPrice","今开价格"),
    HARDEN_PRICE("f51","hardenPrice","涨停价格"),
    DOWN_PRICE("f52","downPrice","跌停价格"),
    YESTERDAY_CLOSING_PRICE("f60","yesterdayClosingPrice","昨收价格"),
    TURNOVER_HAND("f47","turnoverHand","成交量/手"),
    TURNOVER_AMOUNT("f48","turnoverAmount","成交额"),
    SELLVOL("f49","sellvol","外盘"),
    BUYVOL("f161","buyvol","内盘"),
    QUANTITY_RELATIVE_RATIO("f50","quantityRelativeRatio","量比"),
    AVERAGE_PRICE("f71","averagePrice","均价"),
    TRADING_HOUR("f80","tradingHour","交易时间"),
    PLATE("f127","plate","所属模块"),
    SUPER_ORDER_WATER_AMOUNT("f140","superOrderWaterAmount","超大单流水"),
    BIG_ORDER_WATER_AMOUNT("f143","bigOrderWaterAmount","大单流水"),
    MEDIUM_ORDER_WATER_AMOUNT("f146","mediumOrderWaterAmount","中单流水"),
    SMALL_ORDER_WATER_AMOUNT("f149","smallOrderWaterAmount","小单流水"),
    MAIN_STATIC_RATIO("f193","mainStaticRatio","主力净比"),
    SUPER_ORDER_STATIC_RATIO("f194","superOrderStaticRatio","超大单净比"),
    BIG_ORDER_STATIC_RATIO("f195","bigOrderStaticRatio","大单净比"),
    MEDIUM_ORDER_STATIC_RATIO("f196","mediumOrderStaticRatio","中单静比"),
    SMALL_ORDER_STATIC_RATIO("f197","smallOrderStaticRatio","小单净比"),
    TURNOVER_RATE("f168","turnoverRate","换手率"),
    ENTRUST_RATIO("f191","entrustRatio","委比"),
    ENTRUST_BALANCE("f192","entrustBalance","委差");

    private String paramCode;
    private String paramName;
    private String paramExplain;

    EastMoneyStockDailyBasicMessageEnum(String paramCode,String paramName,String paramExplain){
        this.paramCode = paramCode;
        this.paramName = paramName;
        this.paramExplain = paramExplain;
    }

    public String getParamCode() {
        return paramCode;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamExplain() {
        return paramExplain;
    }

    public static EastMoneyStockDailyBasicMessageEnum valOf(String paramCode){
        for(EastMoneyStockDailyBasicMessageEnum eastMoneyStockDailyBasicMessageEnum : values()){
            if(paramCode.equals(eastMoneyStockDailyBasicMessageEnum.paramCode)){
               return eastMoneyStockDailyBasicMessageEnum;
            }
        }
        return null;
    }

}
