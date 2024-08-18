import {Component, OnDestroy, OnInit, Renderer2, ViewChild} from '@angular/core';
import {NavigationEnd, Router, RouterLink, RouterOutlet} from "@angular/router";
import {Subscription} from "rxjs";
import {AppSidebarComponent} from "../nastavnik/nastavnik/app.sidebar.component";
import {AppTopBarComponent} from "../nastavnik/nastavnik/app.topbar.component";
import {LayoutService} from "../nastavnik/nastavnik/service/app.layout.service";
import {filter} from "rxjs/operators";
import {AppFooterComponent} from "../nastavnik/nastavnik/app.footer.component";
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-studentska-sluzba',
  standalone: true,
  imports: [
    RouterLink,
    RouterOutlet,
    AppFooterComponent,
    AppSidebarComponent,
    AppTopBarComponent,
    NgClass
  ],
  templateUrl: './studentska-sluzba.component.html',
  styleUrl: './studentska-sluzba.component.css'
})
export class StudentskaSluzbaComponent implements OnDestroy{

  overlayMenuOpenSubscription: Subscription;

  menuOutsideClickListener: any;

  profileMenuOutsideClickListener: any;

  @ViewChild(AppSidebarComponent) appSidebar!: AppSidebarComponent;

  @ViewChild(AppTopBarComponent) appTopbar!: AppTopBarComponent;

  model1:any[]
  constructor(public layoutService: LayoutService, public renderer: Renderer2, public router: Router) {
    this.model1 = [
      {
        label: 'Administracija Studenata',
        items: [
          { label: 'Upis Studenata', icon: 'pi pi-fw pi-user-plus', routerLink: 'upis-studenata' },
          { label: 'Izdavanje Dokumenata', icon: 'pi pi-fw pi-file', routerLink: 'dokumenta' }
        ]
      },
      {
        label: 'Rasporedi i Provere',
        items: [
          { label: 'Formiranje Rasporeda Nastave', icon: 'pi pi-fw pi-calendar-plus', routerLink: 'raspored-nastave' },
          { label: 'Raspored Ispita', icon: 'pi pi-fw pi-calendar-times', routerLink: 'raspored-ispita' }
        ]
      },
      {
        label: 'Obaveštenja i Udžbenici',
        items: [
          { label: 'Objavljivanje Obaveštenja', icon: 'pi pi-fw pi-bell', routerLink: 'obavestenja' },
          { label: 'Izdavanje Udžbenika', icon: 'pi pi-fw pi-book', routerLink: 'izdavanje-udzbenika' }
        ]
      },
      {
        label: 'Kancelarijski Inventar',
        items: [
          { label: 'Trebovanje Inventara', icon: 'pi pi-fw pi-pencil', routerLink: 'trebovanje-inventara' }
        ]
      }
    ];


    this.overlayMenuOpenSubscription = this.layoutService.overlayOpen$.subscribe(() => {
      if (!this.menuOutsideClickListener) {
        this.menuOutsideClickListener = this.renderer.listen('document', 'click', event => {
          const isOutsideClicked = !(this.appSidebar.el.nativeElement.isSameNode(event.target) || this.appSidebar.el.nativeElement.contains(event.target)
            || this.appTopbar.menuButton.nativeElement.isSameNode(event.target) || this.appTopbar.menuButton.nativeElement.contains(event.target));

          if (isOutsideClicked) {
            this.hideMenu();
          }
        });
      }

      if (!this.profileMenuOutsideClickListener) {
        this.profileMenuOutsideClickListener = this.renderer.listen('document', 'click', event => {
          const isOutsideClicked = !(this.appTopbar.menu.nativeElement.isSameNode(event.target) || this.appTopbar.menu.nativeElement.contains(event.target)
            || this.appTopbar.topbarMenuButton.nativeElement.isSameNode(event.target) || this.appTopbar.topbarMenuButton.nativeElement.contains(event.target));

          if (isOutsideClicked) {
            this.hideProfileMenu();
          }
        });
      }

      if (this.layoutService.state.staticMenuMobileActive) {
        this.blockBodyScroll();
      }
    });

    this.router.events.pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.hideMenu();
        this.hideProfileMenu();
      });
  }

  hideMenu() {
    this.layoutService.state.overlayMenuActive = false;
    this.layoutService.state.staticMenuMobileActive = false;
    this.layoutService.state.menuHoverActive = false;
    if (this.menuOutsideClickListener) {
      this.menuOutsideClickListener();
      this.menuOutsideClickListener = null;
    }
    this.unblockBodyScroll();
  }

  hideProfileMenu() {
    this.layoutService.state.profileSidebarVisible = false;
    if (this.profileMenuOutsideClickListener) {
      this.profileMenuOutsideClickListener();
      this.profileMenuOutsideClickListener = null;
    }
  }

  blockBodyScroll(): void {
    if (document.body.classList) {
      document.body.classList.add('blocked-scroll');
    }
    else {
      document.body.className += ' blocked-scroll';
    }
  }

  unblockBodyScroll(): void {
    if (document.body.classList) {
      document.body.classList.remove('blocked-scroll');
    }
    else {
      document.body.className = document.body.className.replace(new RegExp('(^|\\b)' +
        'blocked-scroll'.split(' ').join('|') + '(\\b|$)', 'gi'), ' ');
    }
  }

  get containerClass() {
    return {
      'layout-theme-light': this.layoutService.config().colorScheme === 'light',
      'layout-theme-dark': this.layoutService.config().colorScheme === 'dark',
      'layout-overlay': this.layoutService.config().menuMode === 'overlay',
      'layout-static': this.layoutService.config().menuMode === 'static',
      'layout-static-inactive': this.layoutService.state.staticMenuDesktopInactive && this.layoutService.config().menuMode === 'static',
      'layout-overlay-active': this.layoutService.state.overlayMenuActive,
      'layout-mobile-active': this.layoutService.state.staticMenuMobileActive,
      'p-input-filled': this.layoutService.config().inputStyle === 'filled',
      'p-ripple-disabled': !this.layoutService.config().ripple
    }
  }

  ngOnDestroy() {
    if (this.overlayMenuOpenSubscription) {
      this.overlayMenuOpenSubscription.unsubscribe();
    }

    if (this.menuOutsideClickListener) {
      this.menuOutsideClickListener();
    }
  }
}
