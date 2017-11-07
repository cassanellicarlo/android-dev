package cassanellicarlo.userdatabase;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.util.StringUtil;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;

public class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUser;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `User`(`id`,`nome`,`cognome`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getId());
        if (value.getNome() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNome());
        }
        if (value.getCognome() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCognome());
        }
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `User` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void insertAll(User... users) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(User user) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<User> getAll() {
    final String _sql = "SELECT * FROM user";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfNome = _cursor.getColumnIndexOrThrow("nome");
      final int _cursorIndexOfCognome = _cursor.getColumnIndexOrThrow("cognome");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpNome;
        _tmpNome = _cursor.getString(_cursorIndexOfNome);
        final String _tmpCognome;
        _tmpCognome = _cursor.getString(_cursorIndexOfCognome);
        _item = new User(_tmpId,_tmpNome,_tmpCognome);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<User> loadAllByIds(int[] userIds) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM user WHERE id IN (");
    final int _inputSize = userIds.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int _item : userIds) {
      _statement.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfNome = _cursor.getColumnIndexOrThrow("nome");
      final int _cursorIndexOfCognome = _cursor.getColumnIndexOrThrow("cognome");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item_1;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpNome;
        _tmpNome = _cursor.getString(_cursorIndexOfNome);
        final String _tmpCognome;
        _tmpCognome = _cursor.getString(_cursorIndexOfCognome);
        _item_1 = new User(_tmpId,_tmpNome,_tmpCognome);
        _result.add(_item_1);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public User findById(int userid) {
    final String _sql = "SELECT * FROM user WHERE id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userid);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfNome = _cursor.getColumnIndexOrThrow("nome");
      final int _cursorIndexOfCognome = _cursor.getColumnIndexOrThrow("cognome");
      final User _result;
      if(_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpNome;
        _tmpNome = _cursor.getString(_cursorIndexOfNome);
        final String _tmpCognome;
        _tmpCognome = _cursor.getString(_cursorIndexOfCognome);
        _result = new User(_tmpId,_tmpNome,_tmpCognome);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public User findByName(String first, String last) {
    final String _sql = "SELECT * FROM user WHERE nome LIKE ? AND cognome LIKE ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (first == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, first);
    }
    _argIndex = 2;
    if (last == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, last);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfNome = _cursor.getColumnIndexOrThrow("nome");
      final int _cursorIndexOfCognome = _cursor.getColumnIndexOrThrow("cognome");
      final User _result;
      if(_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpNome;
        _tmpNome = _cursor.getString(_cursorIndexOfNome);
        final String _tmpCognome;
        _tmpCognome = _cursor.getString(_cursorIndexOfCognome);
        _result = new User(_tmpId,_tmpNome,_tmpCognome);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
