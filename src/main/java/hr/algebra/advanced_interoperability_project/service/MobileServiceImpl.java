package hr.algebra.advanced_interoperability_project.service;

import hr.algebra.advanced_interoperability_project.domain.Mobile;
import hr.algebra.advanced_interoperability_project.dto.MobileDTO;
import hr.algebra.advanced_interoperability_project.mapper.MobileMapper;
import hr.algebra.advanced_interoperability_project.repository.MobileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MobileServiceImpl implements MobileService {
    private final MobileRepository mobileRepository;

    public MobileServiceImpl(MobileRepository mobileRepository) {
        this.mobileRepository = mobileRepository;
    }
    @Override
    public List<MobileDTO> getAllMobiles() {
        return mobileRepository.findAll()
                .stream()
                .map(MobileMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<MobileDTO> getMobileById(Long id) {
        return mobileRepository.findById(id)
                .map(MobileMapper::toDTO);
    }

    @Override
    public void saveMobile(MobileDTO mobileDTO) {
        mobileRepository.save(MobileMapper.toEntity(mobileDTO));
    }

    @Override
    public void updateMobile(Long id, MobileDTO mobileDTO) {
        Optional<Mobile> optionalMobile = mobileRepository.findById(id);
        if (optionalMobile.isPresent()) {
            Mobile mobile = optionalMobile.get();
            MobileMapper.updateEntity(mobileDTO, mobile);
            mobileRepository.save(mobile);
        }
    }

    @Override
    public void deleteMobile(Long id) {
        mobileRepository.deleteById(id);
    }
}
