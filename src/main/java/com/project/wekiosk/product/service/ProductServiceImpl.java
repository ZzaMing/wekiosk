package com.project.wekiosk.product.service;

import com.project.wekiosk.category.domain.Category;
import com.project.wekiosk.category.repository.CategoryRepository;
import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.option.dto.OptionsDTO;
import com.project.wekiosk.option.repository.OptionsRepository;
import com.project.wekiosk.page.dto.PageRequestDTO;
import com.project.wekiosk.page.dto.PageResponseDTO;
import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.product.domain.ProductImage;
import com.project.wekiosk.product.dto.ProductDTO;
import com.project.wekiosk.product.dto.ProductListDTO;
import com.project.wekiosk.product.repository.ProductRepository;
import com.project.wekiosk.util.FileUploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final FileUploader fileUploader;
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final OptionsRepository optionsRepository;

    @Override
    public Optional<Product> findProductInCategory(Long cateno, Long pno) {
        return productRepository.findProductInCategory(cateno, pno);
    }

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO) {
        return null;

    }


    @Override
    @Transactional
    public Long register(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCateno())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다. cateno=" + productDTO.getCateno()));

        Long savedPno = setProduct(productDTO); // setProduct() 메서드를 호출하여 상품과 옵션들을 데이터베이스에 저장, 생성한 상품의 pno 값
        Product savedProduct = productRepository.findById(savedPno)
                .orElseThrow(() -> new NoSuchElementException("생성한 상품을 찾을 수 없습니다. pno=" + savedPno));

        productDTO.getGimages().forEach(fname -> {
            savedProduct.addImage(fname);
        });
        // 상품과 카테고리 연관 관계 설정
        savedProduct.setCategory(category);


        return savedProduct.getPno(); // 생성한 상품의 pno를 반환
    }


    @Override
    public void remove(Long pno) {
        Product product = productRepository.findById(pno)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. pno: " + pno));

        product.changeDel(true);

        List<ProductImage> images = product.getImages();
        List<String> fileNames = images.stream()
                .map(ProductImage::getFname)
                .collect(Collectors.toList());

        // 파일 삭제
        fileUploader.removeFiles(fileNames);

        // 상품 삭제
        productRepository.save(product);
//        productRepository.delete(product);
    }


    public List<ProductDTO> getProductsByCategory(Long cateno) {
        List<Product> result = productRepository.findAllByCategory(cateno);

        List<ProductDTO> dtoList = result.stream()
                .map(product -> ProductDTO.builder()
                        .pprice(product.getPprice())
                        .pname(product.getPname())
                        .gimages(product.getImages().stream().map(img -> img.getFname()).collect(Collectors.toList()))
                        .cateno(product.getCategory().getCateno())
                        .options(product.getOptions().stream().map(option -> OptionsDTO.builder()
                                .pno(option.getProduct().getPno())
                                .ord(option.getOrd())
                                .oname(option.getOname())
                                .oprice(option.getOprice())
                                .build()).collect(Collectors.toList()))
                        .pno(product.getPno())
                        .build())
                .collect(Collectors.toList());

        return dtoList;
    }

    private ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO readOneInCategory(Long cateno, Long pno) {

        Optional<Product> result = productRepository.findProductInCategory(cateno, pno);

        Product product = result.orElseThrow();

        List<MultipartFile> multipartFiles = new ArrayList<>();

//        ProductDTO dto = modelMapper.map(product, ProductDTO.class);
        ProductDTO dto = ProductDTO.builder()
                .pname(product.getPname())
                .pprice(product.getPprice())
                .cateno(product.getCategory().getCateno())
                .options(product.getOptions().stream().map(options -> OptionsDTO.builder()
                        .pno(options.getProduct().getPno())
                        .oname(options.getOname())
                        .oprice(options.getOprice())
                        .ord(options.getOrd())
                        .build()).collect(Collectors.toList()))
                .gimages(product.getImages().stream().map(image -> image.getFname()).collect(Collectors.toList()))
                .pno(product.getPno())
                .build();

        log.info("d >>>>>>> " + dto);

        return dto;
    }

    @Override
    public Long setProduct(ProductDTO productDTO) {
        // 상품 엔티티 생성
        Product product = Product.builder()
                .pname(productDTO.getPname())
                .pprice(productDTO.getPprice())
                .build();

        // 옵션 엔티티 생성 및 상품과 연관 관계 설정
        List<OptionsDTO> optionsDTOList = productDTO.getOptions();
        if (optionsDTOList != null && !optionsDTOList.isEmpty()) {
            for (OptionsDTO optionsDTO : optionsDTOList) {
                Options option = Options.builder()
                        .oname(optionsDTO.getOname())
                        .oprice(optionsDTO.getOprice())
                        .product(product)
                        .build();

                optionsRepository.save(option);
            }
        }


        Product savedProduct = productRepository.save(product);

        return savedProduct.getPno(); // 생성한 상품의 pno를 반환합니다.
    }

    @Override
    public void toggleShowProduct(Long cateno, List<Long> pnoList) {

        // 카테고리에 해당하는 기존 상품들의 상태를 false로 수정
        List<Product> res = productRepository.findAllByCategory(cateno);

        for (Product product : res) {

            product.changeShow(false);

            productRepository.save(product);
        }


        // 키오스크에 보여줄 상품만 true로 수정
        for (Long pno : pnoList) {

            Optional<Product> result = productRepository.findById(pno);

            Product product = result.orElseThrow();

            product.changeShow(true);

            productRepository.save(product);
        }
    }

    @Override
    public List<ProductDTO> showProduct(Long cateno) {

        List<Product> result = productRepository.findShowProduct(cateno);

        List<ProductDTO> dtoList = result.stream()
                .map(product -> ProductDTO.builder()
                        .pprice(product.getPprice())
                        .pname(product.getPname())
                        .gimages(product.getImages().stream().map(img -> img.getFname()).collect(Collectors.toList()))
                        .cateno(product.getCategory().getCateno())
                        .options(product.getOptions().stream().map(option -> OptionsDTO.builder()
                                .pno(option.getProduct().getPno())
                                .ord(option.getOrd())
                                .oname(option.getOname())
                                .oprice(option.getOprice())
                                .build()).collect(Collectors.toList()))
                        .pno(product.getPno())
                        .build())
                .collect(Collectors.toList());

        return dtoList;
    }


    @Override
    public void modifyProduct(Long pno, @ModelAttribute ProductDTO productDTO) {
        Optional<Product> result = productRepository.findById(productDTO.getPno());

        Product product = result.orElseThrow();
        // 상품 정보 업데이트

        product.setPname(productDTO.getPname());
        product.setPprice(productDTO.getPprice());

        // 옵션 정보 업데이트

        List<OptionsDTO> optionsDTOList = productDTO.getOptions();
        List<Options> newOptions = convertToOptionEntities(optionsDTOList, product);

        product.getOptions().clear();
        product.getOptions().addAll(newOptions);


        // 파일 업로드 및 이미지 정보 설정
        List<String> oldFileNames = product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());
        log.info("before---------------------------------" + product.getImages());
        product.clearImages();
        log.info("---------------------------------" + productDTO.getImages());
        log.info("-------GGGGGGGGG--------------------------" + productDTO.getGimages());
//        이미지 문자열들을 추가 addImage( )
        if(productDTO.getGimages() != null) {
        productDTO.getGimages().forEach(fname -> {
            log.info("before fname---------------------------" + fname);
            product.addImage(fname);
            log.info("fname---------------------------"+ fname);
        });
        }
        try {
            log.info(product);
            productRepository.save(product);
        } catch (Exception e) {
            log.error("save ERROR", e);
        }
        log.info("--------------images-------------------");
        if (productDTO.getGimages() != null) {
            List<String> newFiles = productDTO.getGimages();
            List<String> wantDeleteFiles = oldFileNames.stream()
                    .filter(f -> newFiles.indexOf(f) == -1)
                    .collect(Collectors.toList());
            log.info("---------------------------------");
            log.info(wantDeleteFiles);

            fileUploader.removeFiles(wantDeleteFiles);
        }

    }

    private List<Options> convertToOptionEntities(List<OptionsDTO> optionsDTOList, Product existingProduct) {
        List<Options> options = new ArrayList<>();
        if (optionsDTOList != null && !optionsDTOList.isEmpty()) {
            for (OptionsDTO optionsDTO : optionsDTOList) {
                Options option = Options.builder()
                        .oname(optionsDTO.getOname())
                        .oprice(optionsDTO.getOprice())
                        .product(existingProduct) // 기존 Product 엔티티와 연관 관계 설정
                        .build();
                options.add(option);
            }
        }
        return options;
    }

}




