package com.example.martybackend.service;


import com.example.martybackend.constants.OrdConstants;
import com.example.martybackend.dtos.OrdDto;
import com.example.martybackend.dtos.mappers.OrdMapper;
import com.example.martybackend.entity.Ord;
import com.example.martybackend.entity.Product;
import com.example.martybackend.entity.User;
import com.example.martybackend.repository.OrdRepository;
import com.example.martybackend.repository.ProductRepository;
import com.example.martybackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.martybackend.constants.OrdConstants.*;

@Service
public class OrdService extends RuntimeException {
    private final OrdRepository ordRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdRepository.class);

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrdService(OrdRepository ordRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.ordRepository = ordRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    /**
     * This method create an order
     * @param ordDto
     * @return
     */
    public String insert(OrdDto ordDto){
        try{
        Ord ord = OrdMapper.fromDto(ordDto);
        ord = ordRepository.save(ord);
        LOGGER.info(ORD_INSERTED_SUCCESSFULLY);
        return(ORD_INSERTED_SUCCESSFULLY);}
        catch (Exception e){
            LOGGER.info(e.getMessage());
            return null;
        }
    }


    /**
     * This method finds all the orders
     * @return returns a list of orders
     */
    public List<OrdDto> findOrd(){
        try{
        List<Ord> ordList = ordRepository.findAll();
        LOGGER.info(OrdConstants.LOGGER_ORD);
        return ordList.stream().map(OrdMapper::toEntity).collect(Collectors.toList());

        }
        catch (Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * This method search an order by id
     * @param ord_Id the order id we are searching for
     * @return returns the order
     */
    public OrdDto findOrdById(Long ord_Id)
    {  try {
            Optional<Ord> optionalOrd = ordRepository.findById(ord_Id);
            return OrdMapper.toEntity(optionalOrd.get());
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * This method get all orders which are taken by a waiter
     * @param user_Id the waiter's id
     * @return all the orders
     */
    public List<OrdDto> findOrdByUserId(Long user_Id)
    {
        try {
            List<Ord> ordList = ordRepository.findAll();
            List<Ord> finalOrdList = new ArrayList<>();
            for (Ord ord : ordList) {
                if (ord.getUser().getUserId().equals(user_Id)) {
                    finalOrdList.add(ord);
                }
            }
            return finalOrdList.stream().map(OrdMapper::toEntity).collect(Collectors.toList());
        }
        catch(Exception e){
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * This method updates an order
     * @param ordDto the order with the updated information
     * @return  returns success message and updated order
     */
    public String update (OrdDto ordDto){
        try{
        Ord existingOrd = ordRepository.findById(ordDto.getOrd_Id()).get();

        existingOrd.setTableNr(ordDto.getTableNr());
        //existingOrd.setPrice(ordDto.getPrice());
        existingOrd.setUser(ordDto.getUser());
        //existingOrd.setProductList(ordDto.getProductList());

        Ord updatedOrd =  ordRepository.save(existingOrd);
        LOGGER.info(THE_ORDER + updatedOrd.getOrd_Id());
        return ( OrdMapper.toEntity(updatedOrd)+"updatat cu succes!");}
        catch (Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }


    /**
     * This method deletes an order
     * @param ord_Id order's id we want to delete
     * @return success message
     */
    public String delete(Long ord_Id){
        try{
            ordRepository.deleteById(ord_Id);
            LOGGER.info(ORD_DELETED_SUCCESSFULLY);
            return(ORD_DELETED_SUCCESSFULLY);
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * This method adds products to order
     * @param tableNr the table number
     * @param productList the productList
     * @return returns success message
     */


    public void addProductToOrd(int tableNr, List<String> productList) {
        try{
            Long currentOrd = findOrderIdByTableNr(tableNr);
            Optional<Ord> optionalOrd = ordRepository.findById(currentOrd);
            if (optionalOrd.isPresent()) {
                Ord ord = optionalOrd.get();

                List<Product> partial = new ArrayList<>(ord.getProductList());
                for(String id: productList){
                    partial.add((productRepository.findById(Long.valueOf(id)).get()));
                }

                    ord.setProductList(partial);
                    ordRepository.save(ord);

            }
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
        }
    }
    /**
     *This method calculates the order's price
     * @return returns the price
     */

    public float calculateAndUpdateTotalPrice(int tableNr) {

            Long currentOrd = findOrderIdByTableNr(tableNr);
            Optional<Ord> optionalOrd = ordRepository.findById(currentOrd);
            float currentSale;
            float finalPrice = 0;
            if (optionalOrd.isPresent()) {
                Ord ord = optionalOrd.get();
                currentSale = ord.getSale().getPercent();

                List<Product> productList = ord.getProductList();

                if (productList != null && !productList.isEmpty()) {
                    float totalPrice = 0;
                    for (Product product : productList) {
                        totalPrice += product.getPrice();
                    }
                    finalPrice = totalPrice - (totalPrice * currentSale) / 100;
                    // Actualizăm prețul total al comenzii
                    ord.setPrice(finalPrice);
                    ordRepository.save(ord); // Salvăm modificările în baza de date
                    LOGGER.info("The price:" + finalPrice);
                    return finalPrice;
                } else {
                    throw new IllegalArgumentException(PRODUCT_LIST_IS_EMPTY + tableNr);
                }
            } else {
                throw new IllegalArgumentException(ORD_NOT_FOUND + tableNr);
            }


    }

    /**
     * This method returns a list with all users that have role set as "waiter"
     * @param userId waiter's id
     * @return returns a list with all Users that are waiters
     */

    public boolean isUserWaiter(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            String role = optionalUser.get().getRole();

            LOGGER.info(USER_IS_WAITER);
            return "chelner".equalsIgnoreCase(role);
        }
        else {
            LOGGER.error(USER_IS_NOT_WAITER );
            return false;
        }
    }

    /**
     * This method finds an order using table nr
     * @param tableNr the table number of the order
     * @return return order's id
     */
    public Long findOrderIdByTableNr(int tableNr){
        List<Ord> ordList = ordRepository.findAll();
        Long currentId = null;
        for(Ord ord: ordList){
            if(ord.getTableNr() == tableNr)
            {
                 currentId = ord.getOrd_Id();
            }
        }
        return currentId;
    }

    /**
     * This method returns a map which contains product list and quantity
     * @param ord order for counting the products
     * @return returns the map
     */
    public Map<Product,Long> findOrdAndQ(OrdDto ord){
        Map<Product, Long> currentMap = new HashMap<>();

        for(Product p: ord.getProductList()){
            if(!currentMap.containsKey(p)){
                currentMap.put(p,1L);
            }
            else{
                currentMap.put(p,currentMap.get(p)+1L);
            }
        }
        return currentMap;
    }
}