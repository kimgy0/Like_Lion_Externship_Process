package com.shop.projectlion.domain.image.entity;

import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.sub.CommonSubEntity;
import com.shop.projectlion.infra.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Table(name = "item_image")
public class ItemImage extends CommonSubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_id")
    private Long id;

    @Column(name = "image_url")
    private String url;

    @Column(name = "image_name")
    private String name;

    @Column(name = "original_image_name")
    private String originalName;

    @Column(name = "is_rep_image")
    private boolean isRep;

    @JoinColumn(name = "item_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    public void updateImage(UploadFile uploadFile){
        this.url = uploadFile.getFileUploadUrl();
        this.name = uploadFile.getStoreFileName();
        this.originalName = uploadFile.getOriginalFileName();
    }

    public void addItem(Item item){
        this.item = item;
        List<ItemImage> itemImages = item.getItemImages();
        itemImages.add(this);

    }

    public static ItemImage toEntity(UploadFile uploadFile, boolean isRep){
        return ItemImage.builder()
                .isRep(isRep)
                .originalName(uploadFile.getOriginalFileName())
                .name(uploadFile.getStoreFileName())
                .url(uploadFile.getFileUploadUrl())
                .build();
    }
}
