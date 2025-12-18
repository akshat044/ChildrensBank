package in.akshat.branch_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.akshat.branch_service.dto.BranchCreateDto;
import in.akshat.branch_service.dto.BranchResponse;
import in.akshat.branch_service.dto.BranchUpdateDto;
import in.akshat.branch_service.entity.Branch;
import in.akshat.branch_service.mapper.BranchMapper;
import in.akshat.branch_service.service.BranchService;
import jakarta.validation.Valid;

@RefreshScope // for refreshing the bean
@RestController
@RequestMapping("/api/branches") 
public class BranchController {
	  
	@Autowired
	    private BranchService branchService;
	
	@Value("${welcome.message}")
	private String message;
	
	// just for testing purposes of that whether the properties file loaded or not
	@GetMapping("/print")
	public String print() {
		return "from brannch" + message;
	}
	
	    @PostMapping
	    public ResponseEntity<BranchResponse> createBranch(@RequestBody @Valid BranchCreateDto branchCreateDto) {
	    Branch b = 	branchService.createBranch(BranchMapper.toEntity(branchCreateDto));
	    
	    // now again converting into to response dto 
	   BranchResponse response =  BranchMapper.toResponse(b);
	        return ResponseEntity.ok(response);
	    } // here i could add savedbranchdto as well 

	     
	    @PutMapping("/{id}")  
	    public ResponseEntity<BranchResponse> updateBranch(@PathVariable Long id, @RequestBody @Valid BranchUpdateDto branchUpdateDto) {
	    	 
	    	 
	        Branch updated = branchService.updateBranch(id, branchUpdateDto);
	        BranchResponse response = BranchMapper.toResponse(updated);
	      return   ResponseEntity.ok(response);
	    }   

	     
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteBranch(@PathVariable Long id) {
	        branchService.deleteBranch(id);
	        return ResponseEntity.ok("Branch deleted successfully!");
	    }

	     
	    @GetMapping("/{id}")
	    public ResponseEntity<BranchResponse> getBranchById(@PathVariable Long id) {
	        return ResponseEntity.ok(branchService.getBranchById(id));
	    }

	     
	    @GetMapping
	    public ResponseEntity<List<Branch>> getAllBranches() {
	        return ResponseEntity.ok(branchService.getAllBranches());
	    }

	  

	 
	    @GetMapping("/{branchId}/exists")
	    public boolean checkBranchExists(@PathVariable Long branchId) {
	        return branchService.checkBranchExistsById(branchId);
	    }
	    
	 
	    
}
