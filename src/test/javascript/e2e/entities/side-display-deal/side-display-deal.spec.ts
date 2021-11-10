import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SideDisplayDealComponentsPage, SideDisplayDealDeleteDialog, SideDisplayDealUpdatePage } from './side-display-deal.page-object';

const expect = chai.expect;

describe('SideDisplayDeal e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let sideDisplayDealComponentsPage: SideDisplayDealComponentsPage;
  let sideDisplayDealUpdatePage: SideDisplayDealUpdatePage;
  let sideDisplayDealDeleteDialog: SideDisplayDealDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SideDisplayDeals', async () => {
    await navBarPage.goToEntity('side-display-deal');
    sideDisplayDealComponentsPage = new SideDisplayDealComponentsPage();
    await browser.wait(ec.visibilityOf(sideDisplayDealComponentsPage.title), 5000);
    expect(await sideDisplayDealComponentsPage.getTitle()).to.eq('Side Display Deals');
    await browser.wait(
      ec.or(ec.visibilityOf(sideDisplayDealComponentsPage.entities), ec.visibilityOf(sideDisplayDealComponentsPage.noResult)),
      1000
    );
  });

  it('should load create SideDisplayDeal page', async () => {
    await sideDisplayDealComponentsPage.clickOnCreateButton();
    sideDisplayDealUpdatePage = new SideDisplayDealUpdatePage();
    expect(await sideDisplayDealUpdatePage.getPageTitle()).to.eq('Create or edit a Side Display Deal');
    await sideDisplayDealUpdatePage.cancel();
  });

  it('should create and save SideDisplayDeals', async () => {
    const nbButtonsBeforeCreate = await sideDisplayDealComponentsPage.countDeleteButtons();

    await sideDisplayDealComponentsPage.clickOnCreateButton();

    await promise.all([
      sideDisplayDealUpdatePage.typeSelectLastOption(),
      sideDisplayDealUpdatePage.setTitleInput('title'),
      sideDisplayDealUpdatePage.setDescriptionInput('description'),
      sideDisplayDealUpdatePage.setImageUrlInput('imageUrl'),
      sideDisplayDealUpdatePage.setDealUrlInput('dealUrl'),
      sideDisplayDealUpdatePage.setPostedByInput('postedBy'),
      sideDisplayDealUpdatePage.setPostedDateInput('postedDate'),
      sideDisplayDealUpdatePage.setStartDateInput('startDate'),
      sideDisplayDealUpdatePage.setEndDateInput('endDate'),
      sideDisplayDealUpdatePage.setOriginalPriceInput('originalPrice'),
      sideDisplayDealUpdatePage.setCurrentPriceInput('currentPrice'),
      sideDisplayDealUpdatePage.setDiscountInput('discount'),
      sideDisplayDealUpdatePage.setActiveInput('active'),
      sideDisplayDealUpdatePage.positionSelectLastOption(),
      sideDisplayDealUpdatePage.getApprovedInput().click(),
      sideDisplayDealUpdatePage.setCountryInput('country'),
      sideDisplayDealUpdatePage.setCityInput('city'),
      sideDisplayDealUpdatePage.setPinCodeInput('pinCode'),
      sideDisplayDealUpdatePage.merchantDetailsSelectLastOption(),
      sideDisplayDealUpdatePage.dealCategorySelectLastOption(),
    ]);

    await sideDisplayDealUpdatePage.save();
    expect(await sideDisplayDealUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await sideDisplayDealComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SideDisplayDeal', async () => {
    const nbButtonsBeforeDelete = await sideDisplayDealComponentsPage.countDeleteButtons();
    await sideDisplayDealComponentsPage.clickOnLastDeleteButton();

    sideDisplayDealDeleteDialog = new SideDisplayDealDeleteDialog();
    expect(await sideDisplayDealDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Side Display Deal?');
    await sideDisplayDealDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(sideDisplayDealComponentsPage.title), 5000);

    expect(await sideDisplayDealComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
