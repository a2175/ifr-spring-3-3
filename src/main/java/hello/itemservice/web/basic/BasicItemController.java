package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String save(
            @RequestParam("itemName") String itemName,
            @RequestParam("price") int price,
            @RequestParam("quantity") Integer quantity,
            Model model
    ) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        
        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String saveV2(@ModelAttribute Item item, Model model) {
        
        itemRepository.save(item);

        model.addAttribute("item", item); //자동 추가, 생략 가능

        return "basic/item";
    }

//    @PostMapping("/add")
    public String saveV3(@ModelAttribute Item item) {

        itemRepository.save(item);

        return "basic/item";
    }

    @PostMapping("/add")
    public String saveV4(Item item) { //@ModelAttribute 생략 가능

        itemRepository.save(item);

        return "basic/item";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}
