package main.backend.model.user;

public class Customer extends User {
    private Gender gender;
    private Integer age;
    private String phone;
    private String email;
    private String address;

    public Customer(String username, String password, Gender gender, Integer age, String phone, String email, String address) {
        super(username, password, Role.CUSTOMER);
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
