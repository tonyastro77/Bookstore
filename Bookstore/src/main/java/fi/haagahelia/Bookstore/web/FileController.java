package fi.haagahelia.Bookstore.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fi.haagahelia.Bookstore.domain.FileModel;
import fi.haagahelia.Bookstore.domain.FileModelRepository;

@Controller
public class FileController {
		@Autowired
		private FileModelRepository repository;
	
	@Value("${upload.path}")
	private String uploadFolder;

	@GetMapping("/")
	public String index() {
		return "upload";
	}
	
	@PostMapping("/upload")
	public String fileUpload(@RequestParam("file") MultipartFile file, Model model) {
		if (file.isEmpty()) {
			model.addAttribute("msg", "Upload failed");
			return "uploadstatus";
		}
		
		try {
			FileModel fileModel = new FileModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
			repository.save(fileModel);
			
			return "redirect:/files";
			
			}
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return "uploadstatus";
	}
	
	@GetMapping("/files")
	public String fileList(Model model) {
		model.addAttribute("files", repository.findAll());
		return "fileList";
	}
	
	@GetMapping("/file/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
		Optional<FileModel> fileOptional = repository.findById(id);
		
		if(fileOptional.isPresent()) {
			FileModel file = fileOptional.get();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
					.body(file.getFile());
					
		}
		return ResponseEntity.status(404).body(null);
	}
	
}
