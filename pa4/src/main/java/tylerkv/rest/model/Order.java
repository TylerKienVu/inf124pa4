package tylerkv.rest.model;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "rock")

public class Order {
    private String fname = null;
    private String lname = null;
    private String email = null;
    private String phone = null;
    private String shipAddress = null;
    private String shipAddress2 = null;
    private String city = null;
    private String state = null;
    private String zip = null;
    private String cardName = null;
    private String cardNumber = null;
    private String expiration = null;
    private String csc = null;
    private String billAddress = null;
    private String billAddress2 = null;
    private String billCity = null;
    private String billState = null;
    private String billZip = null;
    private String cartString = null;
    
    public String getFname(){
        return this.fname;
    }
    public void setFname(String fname){
        this.fname = fname;
    }
    
    public String getLname(){
        return this.lname;
    }
    public void setLname(String lname){
        this.lname = lname;
    }
    
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getPhone(){
        return this.phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    
    public String getShipAddress(){
        return this.shipAddress;
    }
    public void setShipAddress(String shipAddress){
        this.shipAddress = shipAddress;
    }
    
    public String getShipAddress2(){
        return this.shipAddress2;
    }
    public void setShipAddress2(String shipAddress2){
        this.shipAddress2 = shipAddress2;
    }
    
    public String getCity(){
        return this.city;
    }
    public void setCity(String city){
        this.city = city;
    }
    
    public String getState(){
        return this.state;
    }
    public void setState(String state){
        this.state = state;
    }
    
    public String getZip(){
        return this.zip;
    }
    public void setZip(String zip){
        this.zip = zip;
    }
    
    public String getCardName(){
        return this.cardName;
    }
    public void setCardName(String cardName){
        this.cardName = cardName;
    }
    
    public String getCardNumber(){
        return this.cardNumber;
    }
    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }
    
    public String getExpiration(){
        return this.expiration;
    }
    public void setExpiration(String expiration){
        this.expiration = expiration;
    }
    
    public String getCsc(){
        return this.csc;
    }
    public void setCsc(String csc){
        this.csc = csc;
    }
    
    public String getBillAddress(){
        return this.billAddress;
    }
    public void setBillAddress(String billAddress){
        this.billAddress = billAddress;
    }
    
    public String getBillAddress2(){
        return this.billAddress2;
    }
    public void setBillAddress2(String billAddress2){
        this.billAddress2 = billAddress2;
    }
    
    public String getBillCity(){
        return this.billCity;
    }
    public void setBillCity(String billCity){
        this.billCity = billCity;
    }
    
    public String getBillState(){
        return this.billState;
    }
    public void setBillState(String billState){
        this.billState = billState;
    }
    
    public String getBillZip(){
        return this.billZip;
    }
    public void setBillZip(String billZip){
        this.billZip = billZip;
    }
    
    public String getCartString(){
        return this.cartString;
    }
    public void setCartString(String cartString){
        this.cartString = cartString;
    }
    
}
