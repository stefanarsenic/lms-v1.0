import {Component, OnDestroy, Renderer2, ViewChild} from '@angular/core';

import {AppFooterComponent} from "./app.footer.component";
import {AppSidebarComponent} from "./app.sidebar.component";
import {AppTopBarComponent} from "./app.topbar.component";
import {NavigationEnd, Router, RouterLink, RouterModule, RouterOutlet} from "@angular/router";
import {filter} from "rxjs/operators";
import {Subscription} from "rxjs";
import {SpisakPredmetaComponent} from "../spisak-predmeta/spisak-predmeta.component";
import {NgClass} from "@angular/common";
import {LayoutService} from "./service/app.layout.service";

@Component({
  selector: 'app-nastavnik',
  standalone: true,
  imports: [
    SpisakPredmetaComponent,
    RouterOutlet,
    AppFooterComponent,
    AppSidebarComponent,
    AppTopBarComponent,
    NgClass

  ],
  templateUrl: './nastavnik-profile.component.html',
  styleUrl: './nastavnik-profile.component.css'
})
export class NastavnikProfileComponent implements OnDestroy {

  overlayMenuOpenSubscription: Subscription;

  menuOutsideClickListener: any;

  profileMenuOutsideClickListener: any;

  @ViewChild(AppSidebarComponent) appSidebar!: AppSidebarComponent;

  @ViewChild(AppTopBarComponent) appTopbar!: AppTopBarComponent;

  model1:any=[]

  constructor(public layoutService: LayoutService, public renderer: Renderer2, public router: Router) {
    this.model1 = [
      {
        label: 'Home',
        items: [
          { label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: ['nastavnik'] }
        ]
      },
      {
        label: 'Predmeti',
        items: [
          { label: 'Pregled Predmeta', icon: 'pi pi-fw pi-book', routerLink: 'spisak-predmeta' },
          { label: 'Uređivanje Silabusa', icon: 'pi pi-fw pi-pencil', routerLink: 'uredjivanje-silabusa' },
          { label: 'Raspored Ishoda', icon: 'pi pi-fw pi-calendar', routerLink: ['raspored-ishoda'] },
          { label: 'Instrumenti Evaluacije', icon: 'pi pi-fw pi-file-o', routerLink: ['/predmeti/evaluacija'] },
          { label: 'Upravljanje Obaveštenjima', icon: 'pi pi-fw pi-bell', routerLink: 'uredjivanje-obavestenja' }
        ]
      },
      {
        label: 'Studenti',
        items: [
          { label: 'Spisak Studenata', icon: 'pi pi-fw pi-users', routerLink: ['studenti-spisak'] },
          { label: 'Pretraga Studenata', icon: 'pi pi-fw pi-search', routerLink: 'pretraga-studenata' },
          { label: 'Podaci o Studentu', icon: 'pi pi-fw pi-id-card', routerLink: ['/studenti/podaci'] },
          { label: 'Unos Ocena', icon: 'pi pi-fw pi-check-square', routerLink: ['/studenti/ocene'] }
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
