package com.ksm.robolo.roboloapp.tos;

import java.util.List;

public class ClientTO {

    private Long id;

    private String name;

    private String surname;

    private List<String> telephoneNumbers;

    private String emailAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<String> getTelephoneNumbers() {
        return telephoneNumbers;
    }

    public void setTelephoneNumbers(List<String> telephoneNumbers) {
        this.telephoneNumbers = telephoneNumbers;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientTO)) return false;

        ClientTO clientTO = (ClientTO) o;

        if (getName() != null ? !getName().equals(clientTO.getName()) : clientTO.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(clientTO.getSurname()) : clientTO.getSurname() != null)
            return false;
        if (getTelephoneNumbers() != null ? !getTelephoneNumbers().equals(clientTO.getTelephoneNumbers()) : clientTO.getTelephoneNumbers() != null)
            return false;
        return getEmailAddress() != null ? getEmailAddress().equals(clientTO.getEmailAddress()) : clientTO.getEmailAddress() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (getTelephoneNumbers() != null ? getTelephoneNumbers().hashCode() : 0);
        result = 31 * result + (getEmailAddress() != null ? getEmailAddress().hashCode() : 0);
        return result;
    }
}
