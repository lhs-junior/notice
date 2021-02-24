package com.task.notice.Config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.Data;

@Data
public class ResponseResult
{
    @JsonProperty("isSuccess")
    private String isSuccess;

    @JsonProperty(value = "errorMsg")
    private String errorMsg;

    @JsonProperty(value = "result", defaultValue = "")
    private Object result;

    public ResponseResult setException(Exception e)
    {
        this.setIsSuccess("false");
        this.setErrorMsg(e.getMessage());
        this.setResult(null);
        return this;
    }

    public static ResponseResult createResult(boolean success, String errMsg, JsonObject jsonResult)
    {
        try
        {
            JsonObject responseJson = new JsonObject();
            ObjectMapper objectMapper = new ObjectMapper();

            responseJson.addProperty("isSuccess", success ? "true" : "fasle");
            responseJson.addProperty("errorMsg", errMsg);
            responseJson.add("result", jsonResult);

            ResponseResult responseResult = objectMapper.readValue(responseJson.toString(), ResponseResult.class);

            return responseResult;
        }

        catch (Exception e)
        {
            return new ResponseResult().setException(e);
        }
    }
}
