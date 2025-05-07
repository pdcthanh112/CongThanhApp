package com.congthanh.notificationservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebpushSubscription {

    @Id
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "subscription", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String subscription;
}

/**
 * {
 *     "endpoint": "https://fcm.googleapis.com/fcm/send/abc123...", // URL để gửi thông báo
 *     "keys": {
 *         "p256dh": "BNL...=", // Khóa công khai để mã hóa thông báo
 *         "auth": "xyz..."   // Khóa xác thực
 *     }
 * }
 * endpoint: URL duy nhất do trình duyệt cung cấp, dùng để gửi thông báo Web Push.
 * keys.p256dh: Khóa công khai (theo chuẩn elliptic curve Diffie-Hellman) để mã hóa payload.
 * keys.auth: Khóa bí mật dùng để xác thực thông báo.
 */
