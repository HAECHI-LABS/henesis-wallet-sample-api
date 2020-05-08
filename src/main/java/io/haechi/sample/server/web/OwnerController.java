package io.haechi.sample.server.web;

import io.haechi.sample.server.application.OwnerApplicationService;
import io.haechi.sample.server.application.dto.DeleteOwnerResultDTO;
import io.haechi.sample.server.application.dto.OwnerDTO;
import io.haechi.sample.server.application.dto.TransferOwnerRequestDTO;
import io.haechi.sample.server.application.dto.TransferOwnerResultDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("owner")
public class OwnerController {
    private OwnerApplicationService ownerApplicationService;
    public OwnerController(
            OwnerApplicationService ownerApplicationService
    ) {
        this.ownerApplicationService = ownerApplicationService;
    }

    @GetMapping
    public OwnerDTO findOwner(){
        return this.ownerApplicationService.find();
    }

    @PutMapping
    public TransferOwnerResultDTO transferOwner(
            @RequestBody TransferOwnerRequestDTO request
    ) {
        return this.ownerApplicationService.transfer(request);
    }

    @DeleteMapping
    public DeleteOwnerResultDTO deleteOwner()
    {
        return this.ownerApplicationService.renounce();
    }
}
