package com.trnka.backend.domain.sync;

import com.trnka.backend.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Stores log/history entries of every sync of Student Statistics from raspberry device to the VST.
 * One can check here, which devices to upload the data properly.
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device_to_server_sync_log")
public class DeviceToServerSyncLog extends BaseEntity {
    @NotNull
    private String deviceId;

    @NotNull
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private DeviceToServerSyncStatus status;

    @NotNull
    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private DeviceToServerSyncType type;
}
