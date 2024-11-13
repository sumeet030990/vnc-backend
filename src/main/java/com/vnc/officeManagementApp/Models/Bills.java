package com.vnc.officeManagementApp.Models;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.vnc.officeManagementApp.Utils.Enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Bills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @ManyToOne()
    private Users buyersId;

    @ManyToOne()
    private Users sellersId;

    @ManyToOne()
    private Users transportersId;

    private String fromAddress;
    private String toAddress;
    private float totalBillAmount;

    @Enumerated(EnumType.STRING)
    private Status status;

    private float totalAmountPaid;
    private Date paymentDueDate;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @UpdateTimestamp
    private LocalDateTime updatedDateTime;
}
