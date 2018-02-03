package model;

import java.util.List;
import java.util.Map;

/**
 * Created by igor on 03/02/18.
 */

public class Profile {
    private String username;
    private String email;
    private String name;
    private String lastName;
    private String gender;
    private String occupation;
    private List<String> interest_tags;
    private List<String> specialities;
    private String primaryRole;
    private String dateOfBirth;
    private String github;
    private String instagram;
    private String twitter;
    private String facebook;
    private String flickr;
    private String linkedin;
    private String googleplus;
    private String whatsapp;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private String skype;
    private String cityName;
    //private String country;
    private String zipCode;
    private String street;
    private String streetNumber;
    private String about;

    public Profile(String username, String email, String name, String lastName,
                   String gender, String occupation, List<String> interest_tags,
                   List<String> specialities, String primaryRole, String dateOfBirth,
                   String github, String instagram, String twitter, String facebook,
                   String flickr, String linkedin, String googleplus, String whatsapp,
                   String phoneNumber, String mobilePhoneNumber, String skype,
                   String cityName, String zipCode, String street,
                   String streetNumber, String about) {
        setUsername(username);
        setEmail(email);
        setName(name);
        setLastName(lastName);
        setGender(gender);
        setOccupation(occupation);
        setInterest_tags(interest_tags);
        setSpecialities(specialities);
        setPrimaryRole(primaryRole);
        setDateOfBirth(dateOfBirth);
        setGithub(github);
        setInstagram(instagram);
        setTwitter(twitter);
        setFacebook(facebook);
        setFlickr(flickr);
        setLinkedin(linkedin);
        setGoogleplus(googleplus);
        setWhatsapp(whatsapp);
        setPhoneNumber(phoneNumber);
        setMobilePhoneNumber(mobilePhoneNumber);
        setSkype(skype);
        setCityName(cityName);
        setZipCode(zipCode);
        setStreet(street);
        setStreetNumber(streetNumber);
        setAbout(about);
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public List<String> getInterest_tags() {
        return interest_tags;
    }

    public void setInterest_tags(List<String> interest_tags) {
        this.interest_tags = interest_tags;
    }

    public List<String> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<String> specialities) {
        this.specialities = specialities;
    }

    public String getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(String primaryRole) {
        this.primaryRole = primaryRole;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getFlickr() {
        return flickr;
    }

    public void setFlickr(String flickr) {
        this.flickr = flickr;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getGoogleplus() {
        return googleplus;
    }

    public void setGoogleplus(String googleplus) {
        this.googleplus = googleplus;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
}
