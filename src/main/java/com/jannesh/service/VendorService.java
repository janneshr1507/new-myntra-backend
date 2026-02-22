package com.jannesh.service;

import com.jannesh.dto.vendor.SaveVendorDTO;
import com.jannesh.dto.vendor.VendorDTO;
import com.jannesh.entity.Vendor;
import com.jannesh.repository.VendorRepository;
import com.jannesh.util.mapper.VendorMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepo;
    private final VendorMapper mapper;

    public VendorDTO fetchVendorDetails(UUID vendorId) {
        Vendor vendor = vendorRepo.findById(vendorId)
                .orElseThrow(() -> new EntityNotFoundException("Vendor Not Found"));
        return mapper.toDTO(vendor);
    }

    public VendorDTO createVendor(SaveVendorDTO requestDTO) {
        try {
            Vendor vendor = mapper.toEntity(requestDTO);
            return mapper.toDTO(vendorRepo.save(vendor));
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Vendor with that name already exists");
        }
    }

    public boolean existsByVendorId(UUID vendorId) {
        return vendorRepo.existsById(vendorId);
    }
}
