package com.azure.laundry.laundry.payload.request;

public class AddressRequest {
    public String addressType;

    public String getAddressType() {
        return addressType;
    }
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    public String getApartmentName() {
        return apartmentName;
    }
    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }
    public String getFloorNo() {
        return floorNo;
    }
    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }
    public String getUnitNo() {
        return unitNo;
    }
    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }
    public String getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getSubDistrict() {
        return subDistrict;
    }
    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }
    public void setAlternatePhoneNumber(String alternatePhoneNumber) {
        this.alternatePhoneNumber = alternatePhoneNumber;
    }
    public String getShortNote() {
        return shortNote;
    }
    public void setShortNote(String shortNote) {
        this.shortNote = shortNote;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String apartmentName;
    public String floorNo;
    public String unitNo;
    public String houseNumber;
    public String streetName;
    public String district;
    public String subDistrict;
    public String postalCode;
    public String phoneNumber;
    public String alternatePhoneNumber;
    public String shortNote;
    public double latitude;
    public double longitude;

    public AddressRequest(){}

}
