package com.project.auction;

import com.project.auction.dto.ItemDto;
import com.project.auction.model.Item;
import com.project.auction.model.ItemImage;
import com.project.auction.repository.ItemRepository;
import com.project.auction.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class AuctionProjectApplicationTests {

	ItemService itemService;

	@Autowired
	public AuctionProjectApplicationTests(ItemService itemService) {
		this.itemService = itemService;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void testSaveOrder(){

		// create Order object
		ItemDto itemDto = new ItemDto();

		ItemImage itemImage = new ItemImage();
		itemImage.setBytes(new byte[]{0,1,0});
		itemImage.setFileName("hola");
		itemImage.setContentType("jpg");

		itemDto.setName("a");
		itemDto.setDescription("b");
		itemDto.setStartDate(new Date(new Date().getTime()+1000000));
		itemDto.setFinishDate(new Date(new Date().getTime()+4000000));

		itemDto.getItemImages().add(itemImage);

		Item item = itemService.getItem(itemDto);
		itemService.save(item);

	}

}
