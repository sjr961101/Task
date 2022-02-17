import com.alibaba.fastjson.JSON;
import exception.BusinessException;
import util.HttpRequestUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class FetchTemperature {

    public Optional<Integer> getTemperature(String province, String city, String county)  {
        Map<String,String> resultMap=null;
        try {
            //获取市列表
            Map<String,String> provinceMap =JSON.parseObject(
                    Optional.ofNullable(
                            HttpRequestUtil.sendGet("http://www.weather.com.cn/data/city3jdata/china.html",null)).orElse("")
                    , HashMap.class);
            //获取区列表
            if(!"".equals(Optional.ofNullable(getKey(provinceMap,province)).get())){

                Map<String,String> cityMap =JSON.parseObject(
                        Optional.ofNullable(
                                HttpRequestUtil.sendGet("http://www.weather.com.cn/data/city3jdata/provshi/"+getKey(provinceMap,province)+".html",null)).orElse("")
                        , HashMap.class);
                if(!"".equals(Optional.ofNullable(getKey(cityMap,city)).isPresent())){
                    //获取村列表
                    Map<String,String> countyMap =JSON.parseObject(
                            Optional.ofNullable(
                                    HttpRequestUtil.sendGet("http://www.weather.com.cn/data/city3jdata/station/"+getKey(provinceMap,province)+getKey(cityMap,city)+".html",null)).orElse("")
                            , HashMap.class);
                    if(!"".equals(Optional.ofNullable(getKey(countyMap,county)).isPresent())) {
                        //获得温度
                        resultMap = JSON.parseObject(
                                Optional.ofNullable(
                                        HttpRequestUtil.sendGet("http://www.weather.com.cn/data/sk/" + getKey(provinceMap, province) + getKey(cityMap, city) + getKey(countyMap, county) + ".html", null)).orElse("")
                                , HashMap.class);
                    }
                }



            }


        } catch (BusinessException e) {
            e.printStackTrace();
        }

        return parsing(resultMap);
    }

    //根据map的value获取map的key
    private static String getKey(Map<String,String> map,String value){
        String key="";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if(value.equals(entry.getValue())){
                key=entry.getKey();
            }
        }
        return key;
    }

    public Optional<Integer> parsing(Map resultMap){
        //正常返回
        if(Optional.ofNullable(resultMap).isPresent()){
            AtomicReference<Optional<Integer>> result = new AtomicReference<>(Optional.empty());
            Optional.ofNullable(resultMap.get("weatherinfo")).ifPresent(x->{
                Optional.ofNullable(((Map)x).get("temp")).ifPresent( y ->{
                    result.set(Optional.ofNullable(Integer.valueOf(Double.valueOf(y.toString()).intValue())));
                });
            });
            return result.get();
        }
        //否则返回0
       return Optional.ofNullable(0);

    }
}
