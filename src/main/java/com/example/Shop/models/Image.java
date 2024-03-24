package com.example.Shop.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name; // имя картинки
    @Column(name = "originalFileName")
    private String originalFileName; // исходное имя файла
    @Column(name = "size")
    private Long size; // размер файла
    @Column(name = "contentType")
    private String contentType; // расширение файла (тип контента)
    @Column(name = "isPreviewImage")
    private boolean isPreviewImage; // изображение предварительного просмотра

    @Lob
//    @Column(name = "bytes", columnDefinition = "longblob")
    private byte[] bytes;
//    @Lob // аннотация @Lob - означает что данное поле в базе данных будет хранить в типе LONGBLOB
//    private byte[] bytes; // массив фотографий

    // устанавливаем отношение таблиц фотографий к товару
    // со стороны фотографий - @ManyToOne (много фотографий)
    // со стороны товара - @OneToMany (один товар)
    // cascade - как повлияет удаление сущности фотографии (Image) на сущность товаров (Product)
    // fetch - способ загрузки привязанных сущностей
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

}
