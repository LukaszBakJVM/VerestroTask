package org.example.verestrotask.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.verestrotask.client.account.Account;

import java.util.Objects;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 3, message = "username at least 3 characters")
    private String username;

    private String password;
    @Pattern(regexp = "[0-9]{9}", message = "invalid phone number")
    private String phoneNumber;
    @Email(message = "invalid email")
    private String email;
    @OneToOne
    private Account account;
    @Enumerated(EnumType.STRING)
    private PreferredNotificationChannel preferredNotificationChannel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PreferredNotificationChannel getPreferredNotificationChannel() {
        return preferredNotificationChannel;
    }

    public void setPreferredNotificationChannel(PreferredNotificationChannel preferredNotificationChannel) {
        this.preferredNotificationChannel = preferredNotificationChannel;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(username, client.username) && Objects.equals(password, client.password) && Objects.equals(phoneNumber, client.phoneNumber) && Objects.equals(email, client.email) && preferredNotificationChannel == client.preferredNotificationChannel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, phoneNumber, email, preferredNotificationChannel);
    }
}
