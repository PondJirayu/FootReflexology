package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by lp700 on 10/12/2559.
 */

public class MemberItemDao {

    @SerializedName("id")                       private int id;
    @SerializedName("firstname")                private String firstName;
    @SerializedName("lastname")                 private String lastName;
    @SerializedName("identification_number")    private String identificationNumber;
    @SerializedName("gender")                   private String gender;
    @SerializedName("birthdate")                private Date birthDate;
    @SerializedName("telephone_number")         private String telephoneNumber;
    @SerializedName("house_village")            private String houseVillage;
    @SerializedName("sub_district")             private String subDistrict;
    @SerializedName("district")                 private String district;
    @SerializedName("province")                 private String province;
    @SerializedName("created_at")               private Timestamp createdAt;
    @SerializedName("updated_at")               private Timestamp updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getHouseVillage() {
        return houseVillage;
    }

    public void setHouseVillage(String houseVillage) {
        this.houseVillage = houseVillage;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
