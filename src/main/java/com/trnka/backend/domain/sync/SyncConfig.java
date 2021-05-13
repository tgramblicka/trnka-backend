package com.trnka.backend.domain.sync;

import com.trnka.backend.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SyncConfig extends BaseEntity {
    private Boolean enableDownloadFromServerToDevice;
    private Boolean enableUploadFromDeviceToServer;

}
