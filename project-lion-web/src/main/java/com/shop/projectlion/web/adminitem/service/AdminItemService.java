package com.shop.projectlion.web.adminitem.service;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.image.entity.ItemImage;
import com.shop.projectlion.domain.image.servie.ItemImageService;
import com.shop.projectlion.domain.image.validator.ItemImageValidator;
import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.infra.FileService;
import com.shop.projectlion.infra.UploadFile;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import com.shop.projectlion.web.adminitem.dto.ItemImageDto;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminItemService {

    private final DeliveryService deliveryService;
    private final ItemImageService itemImageService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final FileService fileService;
    private final ItemImageValidator validator;

    private static final int FIRST_IMAGE_INDEX = 0;


    public List<DeliveryDto> findDeliveryList(){
        return deliveryService.findDeliveryDtoList(memberService.getMyMemberId());
    }

    @Transactional
    public Long registerItem(InsertItemDto insertItemDto){

        Delivery delivery = deliveryService.findDeliveryJoinMember(insertItemDto.getDeliveryId());
        Member member = delivery.getMember();

        List<MultipartFile> itemImageFiles = insertItemDto.getItemImageFiles();
        List<ItemImage> itemImages = new ArrayList<>();

        try {
            List<UploadFile> uploadFiles = fileService.storeFiles(itemImageFiles);
            uploadFiles.stream().forEach( uploadFile -> {
                int index = uploadFiles.indexOf(uploadFile);
                ItemImage itemImage = ItemImage.toEntity(uploadFile, index == FIRST_IMAGE_INDEX ? true : false);
                itemImages.add(itemImage);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Item item = Item.registerItem(insertItemDto,delivery,member,itemImages);
        return itemService.createItem(item).getId();

    }

    @Transactional(readOnly = true)
    public UpdateItemDto findUpdateItem(Long itemId) throws IllegalAccessException {
        UpdateItemDto updateFormForItem = itemService.findUpdateFormForItem(itemId);

        isMyItem(memberService.getMyMemberId(), updateFormForItem.getMemberId());
        List<ItemImageDto> itemImageDtos = itemImageService.findItemImageForItem(itemId);

        updateFormForItem.setItemImageDtos(itemImageDtos);

        return updateFormForItem;
    }

    @Transactional
    public void UpdateItem(UpdateItemDto updateItemDto) throws IllegalAccessException {
        Long itemId = updateItemDto.getItemId();
        Long deliveryId = updateItemDto.getDeliveryId();

        Delivery delivery = deliveryService.findDeliveryJoinMember(deliveryId);
        List<ItemImage> findItemImages = itemImageService.findItemImageEntityForItem(itemId);

        updateItemAndImage(findItemImages, delivery ,updateItemDto, itemId);

;
    }

    @Transactional(readOnly = true)
    public boolean isFirstImageExist(UpdateItemDto updateItemDto, int firstImageIndex) {
        return validator.isFirstImagExist(updateItemDto.getOriginalImageNames(), updateItemDto.getItemImageFiles(), firstImageIndex);
    }

    private void updateItemAndImage(List<ItemImage> findItemImages, Delivery delivery, UpdateItemDto updateItemDto, Long itemId) {
        List<MultipartFile> itemImageFiles = updateItemDto.getItemImageFiles();
        try {
            List<UploadFile> uploadFiles = fileService.storeFiles(itemImageFiles);

            uploadFiles.forEach(uploadFile -> {
                ItemImageDto itemImageDto = updateItemDto.getItemImageDtos().get(uploadFiles.indexOf(uploadFile));

                /* change logic */

                for (ItemImage itemImage : findItemImages) {
                    if(itemImage.getId() == itemImageDto.getItemImageId()){
                        if(validator.isChangeImage(uploadFile, itemImageDto.getOriginalImageName())) {
                            fileService.deleteFile(itemImage.getUrl());
                            itemImage.updateImage(uploadFile);
                        }
                        if(validator.isAddImage(uploadFile, itemImageDto.getOriginalImageName())){
                            itemImage.updateImage(uploadFile);
                        }
                        if(validator.isDeleteImage(uploadFile, itemImageDto.getOriginalImageName())){
                            fileService.deleteFile(itemImage.getUrl()==null ? "" : itemImage.getUrl());
                            itemImage.updateImage(uploadFile);
                        }
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            updateItem(findItemImages, delivery, updateItemDto);
        }
    }

    private void updateItem(List<ItemImage> findItemImages, Delivery delivery, UpdateItemDto updateItemDto) {
        Item item = findItemImages.get(FIRST_IMAGE_INDEX).getItem();
        item.updateItem(updateItemDto, delivery);
    }

    private void isMyItem(Long memberId, Long requestMemberId) throws IllegalAccessException {
        if(memberId != requestMemberId) {
            throw new IllegalAccessException(ErrorCode.NOT_FOUND_ITEM.getMessage());
        }
    }

}
