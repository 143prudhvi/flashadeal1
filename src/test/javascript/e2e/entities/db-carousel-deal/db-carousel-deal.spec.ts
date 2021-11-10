import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DbCarouselDealComponentsPage, DbCarouselDealDeleteDialog, DbCarouselDealUpdatePage } from './db-carousel-deal.page-object';

const expect = chai.expect;

describe('DbCarouselDeal e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let dbCarouselDealComponentsPage: DbCarouselDealComponentsPage;
  let dbCarouselDealUpdatePage: DbCarouselDealUpdatePage;
  let dbCarouselDealDeleteDialog: DbCarouselDealDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DbCarouselDeals', async () => {
    await navBarPage.goToEntity('db-carousel-deal');
    dbCarouselDealComponentsPage = new DbCarouselDealComponentsPage();
    await browser.wait(ec.visibilityOf(dbCarouselDealComponentsPage.title), 5000);
    expect(await dbCarouselDealComponentsPage.getTitle()).to.eq('Db Carousel Deals');
    await browser.wait(
      ec.or(ec.visibilityOf(dbCarouselDealComponentsPage.entities), ec.visibilityOf(dbCarouselDealComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DbCarouselDeal page', async () => {
    await dbCarouselDealComponentsPage.clickOnCreateButton();
    dbCarouselDealUpdatePage = new DbCarouselDealUpdatePage();
    expect(await dbCarouselDealUpdatePage.getPageTitle()).to.eq('Create or edit a Db Carousel Deal');
    await dbCarouselDealUpdatePage.cancel();
  });

  it('should create and save DbCarouselDeals', async () => {
    const nbButtonsBeforeCreate = await dbCarouselDealComponentsPage.countDeleteButtons();

    await dbCarouselDealComponentsPage.clickOnCreateButton();

    await promise.all([
      dbCarouselDealUpdatePage.typeSelectLastOption(),
      dbCarouselDealUpdatePage.setTitleInput('title'),
      dbCarouselDealUpdatePage.setDescriptionInput('description'),
      dbCarouselDealUpdatePage.setImageUrlInput('imageUrl'),
      dbCarouselDealUpdatePage.setDealUrlInput('dealUrl'),
      dbCarouselDealUpdatePage.setPostedByInput('postedBy'),
      dbCarouselDealUpdatePage.setPostedDateInput('postedDate'),
      dbCarouselDealUpdatePage.setStartDateInput('startDate'),
      dbCarouselDealUpdatePage.setEndDateInput('endDate'),
      dbCarouselDealUpdatePage.setOriginalPriceInput('originalPrice'),
      dbCarouselDealUpdatePage.setCurrentPriceInput('currentPrice'),
      dbCarouselDealUpdatePage.setDiscountInput('discount'),
      dbCarouselDealUpdatePage.setActiveInput('active'),
      dbCarouselDealUpdatePage.positionSelectLastOption(),
      dbCarouselDealUpdatePage.getApprovedInput().click(),
      dbCarouselDealUpdatePage.setCountryInput('country'),
      dbCarouselDealUpdatePage.setCityInput('city'),
      dbCarouselDealUpdatePage.setPinCodeInput('pinCode'),
      dbCarouselDealUpdatePage.merchantDetailsSelectLastOption(),
      dbCarouselDealUpdatePage.dealCategorySelectLastOption(),
    ]);

    await dbCarouselDealUpdatePage.save();
    expect(await dbCarouselDealUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await dbCarouselDealComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DbCarouselDeal', async () => {
    const nbButtonsBeforeDelete = await dbCarouselDealComponentsPage.countDeleteButtons();
    await dbCarouselDealComponentsPage.clickOnLastDeleteButton();

    dbCarouselDealDeleteDialog = new DbCarouselDealDeleteDialog();
    expect(await dbCarouselDealDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Db Carousel Deal?');
    await dbCarouselDealDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(dbCarouselDealComponentsPage.title), 5000);

    expect(await dbCarouselDealComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
