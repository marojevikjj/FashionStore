package onlineshopapp.fashionstore.junit;

import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.UserVoucher;
import onlineshopapp.fashionstore.model.Voucher;
import onlineshopapp.fashionstore.repository.UserVoucherRepository;
import onlineshopapp.fashionstore.service.impl.UserVoucherServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserVoucherTest {

    @Mock
    private UserVoucherRepository userVoucherRepository;

    private UserVoucherServiceImpl service;

    @Before
    public void init() {

        UserVoucher uv1 = new UserVoucher();
        uv1.setId((long) 7);
        uv1.setVoucher(new Voucher("v1", 1));
        uv1.setQuantity(1);

        UserVoucher uv2 = new UserVoucher();
        uv2.setId((long) 8);
        uv2.setVoucher(new Voucher("v2", 2));
        uv2.setQuantity(2);

        List<UserVoucher> uvList = new ArrayList<>();
        uvList.add(uv1);
        uvList.add(uv2);

        Mockito.when(this.userVoucherRepository.findUserVouchersByUser(Mockito.any(User.class)))
                .thenReturn(uvList);

        Mockito.when(this.userVoucherRepository.save(Mockito.any(UserVoucher.class)))
                .thenReturn(uv2);

        this.service = Mockito.spy(new UserVoucherServiceImpl(this.userVoucherRepository));
    }

    @Test
    public void searchByUserAndVoucherTest() {

        UserVoucher uv = new UserVoucher();
        uv.setId((long) 7);
        uv.setVoucher(new Voucher("v1", 1));

        Assert.assertEquals(uv, service.searchByUserAndVoucher(new User(), java.util.Optional.of(new Voucher("v1", 1))));
    }

    @Test
    public void searchByUserAndVoucherNotPresentTest() {

        Assert.assertNull(service.searchByUserAndVoucher(new User(), Optional.empty()));
    }

    @Test
    public void searchByUserNotPresentAndVoucherTest() {

        Assert.assertNull(service.searchByUserAndVoucher(null, java.util.Optional.of(new Voucher())));
    }

    @Test
    public void deleteTest() {

        UserVoucher uv = new UserVoucher();
        uv.setId((long) 7);
        uv.setVoucher(new Voucher("v1", 1));
        uv.setQuantity(1);

        Assert.assertEquals(uv, service.delete(new Voucher("v1", 1), new User()));
    }

    @Test
    public void updateQuantityDeleteTest() {

        UserVoucher uv = new UserVoucher();
        uv.setId((long) 8);
        uv.setVoucher(new Voucher("v2", 2));
        uv.setQuantity(2);

        Assert.assertEquals(uv, service.delete(new Voucher("v2", 2), new User()));
    }

    @Test
    public void notFoundVoucherDelete() {

        Assert.assertNull(service.delete(new Voucher(), new User()));
    }

}


