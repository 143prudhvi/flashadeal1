import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DealUserRelationComponentsPage, DealUserRelationDeleteDialog, DealUserRelationUpdatePage } from './deal-user-relation.page-object';

const expect = chai.expect;

describe('DealUserRelation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let dealUserRelationComponentsPage: DealUserRelationComponentsPage;
  let dealUserRelationUpdatePage: DealUserRelationUpdatePage;
  let dealUserRelationDeleteDialog: DealUserRelationDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DealUserRelations', async () => {
    await navBarPage.goToEntity('deal-user-relation');
    dealUserRelationComponentsPage = new DealUserRelationComponentsPage();
    await browser.wait(ec.visibilityOf(dealUserRelationComponentsPage.title), 5000);
    expect(await dealUserRelationComponentsPage.getTitle()).to.eq('Deal User Relations');
    await browser.wait(
      ec.or(ec.visibilityOf(dealUserRelationComponentsPage.entities), ec.visibilityOf(dealUserRelationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DealUserRelation page', async () => {
    await dealUserRelationComponentsPage.clickOnCreateButton();
    dealUserRelationUpdatePage = new DealUserRelationUpdatePage();
    expect(await dealUserRelationUpdatePage.getPageTitle()).to.eq('Create or edit a Deal User Relation');
    await dealUserRelationUpdatePage.cancel();
  });

  it('should create and save DealUserRelations', async () => {
    const nbButtonsBeforeCreate = await dealUserRelationComponentsPage.countDeleteButtons();

    await dealUserRelationComponentsPage.clickOnCreateButton();

    await promise.all([dealUserRelationUpdatePage.setUserIdInput('userId'), dealUserRelationUpdatePage.setDealIdInput('dealId')]);

    await dealUserRelationUpdatePage.save();
    expect(await dealUserRelationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await dealUserRelationComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DealUserRelation', async () => {
    const nbButtonsBeforeDelete = await dealUserRelationComponentsPage.countDeleteButtons();
    await dealUserRelationComponentsPage.clickOnLastDeleteButton();

    dealUserRelationDeleteDialog = new DealUserRelationDeleteDialog();
    expect(await dealUserRelationDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Deal User Relation?');
    await dealUserRelationDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(dealUserRelationComponentsPage.title), 5000);

    expect(await dealUserRelationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
