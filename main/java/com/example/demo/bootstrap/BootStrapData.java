package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.OutsourcedPartService;
import com.example.demo.service.OutsourcedPartServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 *
 *
 *
 *
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository=outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        //for(OutsourcedPart part:outsourcedParts){
        //    System.out.println(part.getName()+" "+part.getCompanyName());
        //}

        /*
        Product bicycle= new Product("bicycle",100.0,15);
        Product unicycle= new Product("unicycle",100.0,15);
        productRepository.save(bicycle);
        productRepository.save(unicycle);
        */

        partRepository.deleteAll();
        productRepository.deleteAll();

        if(partRepository.count() == 0) {

            InhousePart beef = new InhousePart();
            beef.setInv(10);
            beef.setPrice(7.00);
            beef.setName("Beef");
            beef.setMinInv(5);
            beef.setMaxInv(100);
            partRepository.save(beef);

            InhousePart tomato = new InhousePart();
            tomato.setInv(10);
            tomato.setPrice(2.00);
            tomato.setName("Tomatoes");
            tomato.setMinInv(5);
            tomato.setMaxInv(100);
            partRepository.save(tomato);

            InhousePart lettuce = new InhousePart();
            lettuce.setInv(10);
            lettuce.setPrice(1.00);
            lettuce.setName("Lettuce");
            lettuce.setMinInv(5);
            lettuce.setMaxInv(100);
            partRepository.save(lettuce);

            InhousePart cheese = new InhousePart();
            cheese.setInv(10);
            cheese.setPrice(5.00);
            cheese.setName("Cheese");
            cheese.setMinInv(5);
            cheese.setMaxInv(100);
            partRepository.save(cheese);

            InhousePart tortilla = new InhousePart();
            tortilla.setInv(10);
            tortilla.setPrice(3.00);
            tortilla.setName("Tortilla");
            tortilla.setMinInv(5);
            tortilla.setMaxInv(100);
            partRepository.save(tortilla);
        }

        if(productRepository.count() == 0) {

            Product taco = new Product("Taco", 40.00, 0);
            productRepository.save(taco);

            Product quesadilla = new Product("Quesadilla", 60.00, 0);
            productRepository.save(quesadilla);

            Product tostada = new Product("Tostada", 50.00, 0);
            productRepository.save(tostada);

            Product burrito = new Product("Burrito", 30.00, 0);
            productRepository.save(burrito);

            Product tacoSalad = new Product("Taco Salad", 90.00, 0);
            productRepository.save(tacoSalad);
        }










        System.out.println("Started in Bootstrap");
        System.out.println("Number of Products"+productRepository.count());
        System.out.println(productRepository.findAll());
        System.out.println("Number of Parts"+partRepository.count());
        System.out.println(partRepository.findAll());

    }
}
