package org.rental.tools.dto;



import java.util.Objects;

public class Tool {
    private String toolCode;
    private  String toolType;
    private  String brand;

    public static Tool getToolObject(String toolString) throws Exception {
        return parseToolToToolObject(toolString);
    }

    private static Tool parseToolToToolObject(String toolString) throws Exception {
        String[] toolTokens = toolString.split("#");
        if (toolTokens.length == 3 &&
                toolTokens[0] != null && !toolTokens[0].trim().isEmpty() &&
                toolTokens[1] != null && !toolTokens[1].trim().isEmpty() &&
                toolTokens[2] != null && !toolTokens[2].trim().isEmpty()) {
            return new Tool(toolTokens[0], toolTokens[1], toolTokens[2]);
        }
        throw new Exception("Tool string is not correct to create Tool object");
    }

    private Tool(String toolCode, String toolType, String brand) {
            this.toolCode = toolCode;
            this.toolType = toolType;
            this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tool that = (Tool) o;
        return Objects.equals(toolCode, that.toolCode) && Objects.equals(toolType, that.toolType) && Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toolCode, toolType, brand);
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "toolCode='" + toolCode + '\'' +
                ", toolType='" + toolType + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
