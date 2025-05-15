package com.project.hrm.services.impl;

import com.project.hrm.dto.departmentDTO.DepartmentCreateDTO;
import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.dto.departmentDTO.DepartmentUpdateDTO;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.Employees;
import com.project.hrm.mapper.DepartmentMapper;
import com.project.hrm.repositories.DepartmentRepository;
import com.project.hrm.services.DepartmentService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.specifications.DepartmentSpecification;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final @Lazy EmployeeService employeeService;

    /**
     * Tìm kiếm Phòng ban theo mã phòng ban
     *
     * @param id mã phòng ban cần tìm
     * @return đối tượng {@link Departments} đại diện cho phòng ban tìm thấy.
     * Trả về trực tiếp Entity phục vụ cho các Service khác
     */
    @Transactional(readOnly = true)
    @Override
    public Departments findById(Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow();
    }

    /**
     * Tìm kiếm Phòng ban theo mã phòng ban
     *
     * @param id mã phòng ban cần tìm
     * @return đối tượng {@link DepartmentDTO} đại diện cho phòng ban tìm thấy.
     * Trả về DTO Entity phục vụ cho yêu cầu từ Controller
     */
    @Transactional(readOnly = true)
    @Override
    public DepartmentDTO getById(Integer id) {
        return departmentMapper.toDepartmentDTO(departmentRepository.findById(id)
                .orElseThrow());
    }

    /**
     * Tạo mới một phòng ban dựa trên thông tin được cung cấp trong {@link DepartmentCreateDTO}.
     * Phương thức này chuyển đổi DTO sang entity, tự sinh ID mới và lưu vào cơ sở dữ liệu.
     *
     * @param departmentCreateDTO đối tượng chứa thông tin phòng ban cần tạo mới.
     * @return đối tượng {@link DepartmentDTO} đại diện cho phòng ban vừa được tạo.
     */
    @Override
    public DepartmentDTO create(DepartmentCreateDTO departmentCreateDTO) {
        Departments departments = new Departments();

        departments = departmentMapper.convertCreateToEntity(departmentCreateDTO);

        departments.setId(getGenerationId());

        return departmentMapper.toDepartmentDTO(
                departmentRepository.save(departments)
        );
    }

    /**
     * Chỉnh sửa một phòng ban dựa trên thông tin được cung cấp trong {@link DepartmentUpdateDTO}.
     *
     * @param departmentUpdateDTO đối tượng chứa thông tin phòng ban cập nhật.
     * @return đối tượng {@link DepartmentDTO} sau khi cập nhật.
     */
    @Override
    public DepartmentDTO update(DepartmentUpdateDTO departmentUpdateDTO) {
        Departments department = findById(departmentUpdateDTO.getId());

        if(departmentUpdateDTO.getDepartmentName()!=null){
            department.setDepartmentName(department.getDepartmentName());
        }

        if(departmentUpdateDTO.getPhone()!=null){
            department.setPhone(departmentUpdateDTO.getPhone());
        }

        if(departmentUpdateDTO.getAddress()!=null){
            department.setAddress(departmentUpdateDTO.getAddress());
        }

        if(departmentUpdateDTO.getEmail()!=null){
            department.setAddress(departmentUpdateDTO.getAddress());
        }

        if(departmentUpdateDTO.getDescription()!=null){
            department.setDescription(departmentUpdateDTO.getDescription());
        }

        return departmentMapper.toDepartmentDTO(departmentRepository.save(department));
    }

    /**
     * Lọc danh sách phòng ban dựa theo các tiêu chí tên phòng ban, địa chỉ và email.
     * Phương thức sử dụng phân trang và chỉ thực hiện truy vấn đọc (read-only transaction).
     *
     * @param departmentName tên phòng ban cần tìm kiếm (có thể là chuỗi con).
     * @param address địa chỉ của phòng ban (có thể để trống).
     * @param email email của phòng ban (có thể để trống).
     * @param page số trang (tính từ 0).
     * @param size số lượng bản ghi mỗi trang.
     * @return trang kết quả chứa các {@link DepartmentDTO} phù hợp với điều kiện lọc.
     */
    @Transactional(readOnly = true)
    @Override
    public Page<DepartmentDTO> filterDepartment(String departmentName, String address, String email, int page, int size) {
        Specification<Departments> departmentsSpecification = DepartmentSpecification.filterDepartment(departmentName, address, email);

        Pageable pageable = PageRequest.of(page, size);

        return departmentMapper.convertPageEntityToPageDTO(
                departmentRepository.findAll(pageable, departmentsSpecification)
        );
    }

    /**
     * Phân bổ nhân viên làm trưởng phòng ban
     *
     * @param departmentId mã phòng ban.
     * @param employeeId mã nhân viên phân công.
     *
     * @return đối tượng {@link DepartmentDTO} .
     */
    @Override
    public DepartmentDTO appointmentOfDean(Integer departmentId, Integer employeeId) {
        Employees employee = employeeService.findById(employeeId);

        Departments departments = findById(departmentId);

        departments.setDean(employee);

        return departmentMapper.toDepartmentDTO(
                departmentRepository.save(departments)
        );
    }


    private Integer getGenerationId(){
        UUID uuid = UUID.randomUUID();

        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
