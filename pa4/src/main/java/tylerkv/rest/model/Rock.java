package tylerkv.rest.model;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "rock")
public class Rock {
    int rock_id;
    String color;
    int quantity_per_order;
    float price_per_order;
    String description;
    
    public Rock() {

    }
    
    public int getRockId(){
        return rock_id;
    }
    
    public void setRockId(int rock_id){
        this.rock_id = rock_id;
    }
    
    public String getColor(){
        return this.color;
    }
    
    public void setColor(String color){
        this.color = color;
    }
    
    public int getQuantityPerOrder(){
        return this.quantity_per_order;
    }
    
    public void setQuantityPerOrder(int quantity_per_order){
        this.quantity_per_order = quantity_per_order;
    }
    
    public float getPricePerOrder(){
        return this.price_per_order;
    }
    
    public void setPricePerOrder(float price_per_order){
        this.price_per_order = price_per_order;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
}
