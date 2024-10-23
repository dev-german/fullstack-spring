import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { UserInfoService } from 'src/app/services/userinfo/user-info.service';

@Component({
  selector: 'app-header-bar',
  templateUrl: './header-bar.component.html',
  styleUrls: ['./header-bar.component.scss']
})
export class HeaderBarComponent {

  items: Array<MenuItem> = [
    {
      label: 'Profile',
      icon: 'pi pi-user',
    },
    {
      label: 'Settings',
      icon: 'pi pi-cog',
    },
    {
      separator: true
    },
    {
      label: 'Sign out',
      icon: 'pi pi-sign-out',
      command: () => {
        localStorage.clear();
        this.router.navigate(['login']);
      }
    }
  ]

  constructor(
    private userInfoService: UserInfoService,
    private router: Router
  ) {}

  get username(): string {
    return this.userInfoService.getUsername()
  }


  get userRole(): string {
    return this.userInfoService.getUserRole()
  }

}
