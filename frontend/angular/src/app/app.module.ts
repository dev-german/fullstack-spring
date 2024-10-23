import { NgModule } from '@angular/core';
import { BrowserModule  } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { AvatarModule } from 'primeng/avatar';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { MenuModule } from 'primeng/menu';
import { SidebarModule } from 'primeng/sidebar';
import { MessageModule } from 'primeng/message';
import { CardModule } from 'primeng/card';
import { BadgeModule } from 'primeng/badge';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { CustomerComponent } from './components/customer/customer.component';
import { MenuBarComponent } from './components/menu-bar/menu-bar.component';
import { MenuItemComponent } from './components/menu-item/menu-item.component';
import { HeaderBarComponent } from './components/header-bar/header-bar.component';
import { ManageCustomerComponent } from './components/manage-customer/manage-customer.component';
import { LoginComponent } from './components/login/login.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HttpInterceptorService } from './services/interceptor/http-interceptor.service';
import { CustomerCardComponent } from './components/customer-card/customer-card.component';
import { ConfirmationService, MessageService } from 'primeng/api';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { RegisterComponent } from './components/register/register.component';

@NgModule({
  declarations: [
    AppComponent,
    CustomerComponent,
    MenuBarComponent,
    MenuItemComponent,
    HeaderBarComponent,
    ManageCustomerComponent,
    LoginComponent,
    CustomerCardComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    InputTextModule,
    AvatarModule,
    ButtonModule,
    RippleModule,
    MenuModule,
    SidebarModule,
    MessageModule,
    CardModule,
    BadgeModule,
    ToastModule,
    ConfirmDialogModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    },
    MessageService,
    ConfirmationService,
    { provide: JWT_OPTIONS, 
      useValue: JWT_OPTIONS 
    },
    JwtHelperService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
